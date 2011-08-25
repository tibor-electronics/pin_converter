/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.parsing;

/**
 * ILexer
 * 
 * @author Kevin Lindsey
 * @version 1.0
 * @param <T>
 *            any type of object
 */
public interface ILexer<T> {
	/**
	 * advance
	 * 
	 * @return Lexeme<T>
	 */
	Lexeme<T> advance();

	/**
	 * getCurrentLexeme
	 * 
	 * @return Lexeme<T>
	 */
	Lexeme<T> getCurrentLexeme();

	/**
	 * getOffset
	 * 
	 * @return int
	 */
	int getOffset();

	/**
	 * getSource
	 * 
	 * @return String
	 */
	String getSource();

	/**
	 * hasNext
	 * 
	 * @return boolean
	 */
	boolean hasNext();

	/**
	 * setSource
	 * 
	 * @param source
	 */
	void setSource(String source);
}
