/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.parsing;

import java.util.EnumSet;
import java.util.Set;

import com.kevlindev.parsing.Lexer;

/**
 * BoardDefinitionLexer
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class BoardDefinitionLexer extends Lexer<BoardDefinitionTokenType> {
	/**
	 * This is a set of all tokens this lexer generates
	 */
	private static final Set<BoardDefinitionTokenType> TOKENS = EnumSet.allOf(BoardDefinitionTokenType.class);

	/**
	 * This is a list of tokens that the lexer will match but that should be
	 * ignored
	 */
	private static final Set<BoardDefinitionTokenType> FILTERED_TOKENS = EnumSet.of(BoardDefinitionTokenType.WHITESPACE, BoardDefinitionTokenType.COMMENT);

	@Override
	protected BoardDefinitionTokenType getEOFType() {
		return BoardDefinitionTokenType.EOF;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.parsing.Lexer#getFilteredTokens()
	 */
	@Override
	protected Set<BoardDefinitionTokenType> getFilteredTokens() {
		return FILTERED_TOKENS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.parsing.Lexer#getTokens()
	 */
	@Override
	protected Set<BoardDefinitionTokenType> getTokens() {
		return TOKENS;
	}
}
