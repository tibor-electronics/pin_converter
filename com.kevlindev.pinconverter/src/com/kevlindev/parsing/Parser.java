/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.parsing;

import java.text.ParseException;
import java.util.Set;

import com.kevlindev.pinconverter.parsing.ErrorReporter;

/**
 * Parser
 * 
 * @author Kevin Lindsey
 * @version 1.0
 * @param <T>
 *            any type of enumeration
 */
public abstract class Parser<T extends Enum<T>> implements IParser<T> {
	/**
	 * The scanner used to generate tokens for this parser
	 */
	private ILexer<T> lexer;

	/**
	 * advance
	 * 
	 * @return Lexeme<T>
	 */
	protected Lexeme<T> advance() {
		return getLexer().advance();
	}

	/**
	 * createLexer
	 * 
	 * @return ILexer<T>
	 */
	protected abstract ILexer<T> createLexer();

	/**
	 * getCurrentLexeme
	 * 
	 * @return Lexeme<T>
	 */
	protected Lexeme<T> getCurrentLexeme() {
		return getLexer().getCurrentLexeme();
	}

	/**
	 * getLexer
	 * 
	 * @return ILexer<T>
	 */
	protected ILexer<T> getLexer() {
		if (lexer == null) {
			lexer = createLexer();
		}

		return lexer;
	}

	/**
	 * getOffset
	 * 
	 * @return int
	 */
	protected int getOffset() {
		return getLexer().getOffset();
	}

	/**
	 * getText
	 * 
	 * @return String
	 */
	protected String getText() {
		return getCurrentLexeme().text;
	}

	/**
	 * isType
	 * 
	 * @param types
	 * @return boolean
	 */
	protected boolean isType(Set<T> types) {
		Lexeme<T> currentLexeme = getCurrentLexeme();

		return types.contains(currentLexeme.type);
	}

	/**
	 * isType
	 * 
	 * @param type
	 * @return boolean
	 */
	protected boolean isType(T type) {
		Lexeme<T> currentLexeme = getCurrentLexeme();

		return currentLexeme.type == type; // $codepro.audit.disable useEquals
	}

	/**
	 * throwError
	 * 
	 * @param message
	 * @throws ParseException
	 */
	protected void throwError(String message) throws ParseException {
		int offset = getOffset();
		ErrorReporter reporter = new ErrorReporter(getLexer().getSource(), offset);

		throw new ParseException(reporter.getErrorMessage(message), offset);
	}
}
