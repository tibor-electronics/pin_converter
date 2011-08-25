/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.parsing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.kevlindev.parsing.ITokenMatcher;

/**
 * TokenType
 */
public enum BoardDefinitionTokenType implements ITokenMatcher {
	// @formatter:off
	EOF(null),
	WHITESPACE("\\s+"),
	COMMENT("#[^\\r\\n]*$"),
	EOL("\\r|\\n|\\r\\n"),
	IDENTIFIER("[-_a-zA-Z][-_a-zA-Z0-9]*(?:\\([0-9]+\\))?"),
	NUMBER("[0-9]+(?:\\.[0-9]+)?(?:ns\\b)?"),
	HEADER("@header"),
	CONFIG("@config"),
	COMMA(","),
	COLON(":"),
	EQUAL("="),
	STRING("\"[^\"\\r\\n]*\""),
	MULTILINE_STRING("<<.*?>>"),
	LCURLY("\\{"),
	RCURLY("\\}"),
	LBRACKET("\\["),
	RBRACKET("\\]");
	// @formatter:on

	/**
	 * A compiled pattern used when matching a enumeration in a string
	 */
	private Pattern pattern;

	/**
	 * BoardDefinitionTokenType
	 * 
	 * @param regex
	 */
	private BoardDefinitionTokenType(String regex) {
		if (regex != null && regex.length() > 0) {
			try {
				pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL);
			} catch (PatternSyntaxException e) {
				e.printStackTrace();
			}
		} else {
			pattern = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.parsing.ITokenMatcher#getMatcher(java.lang.String)
	 */
	public Matcher getMatcher(String source) {
		Matcher result = null;

		if (pattern != null) {
			result = pattern.matcher(source);
		}

		return result;
	}

	/**
	 * getPattern
	 * 
	 * @return Pattern
	 */
	public Pattern getPattern() {
		return pattern;
	}
}
