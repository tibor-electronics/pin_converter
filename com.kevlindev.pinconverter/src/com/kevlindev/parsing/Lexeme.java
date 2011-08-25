/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.parsing;

/**
 * Lexeme
 * 
 * @author Kevin Lindsey
 * @version 1.0
 * @param <T>
 *            any type of object
 */
public class Lexeme<T> {
	/**
	 * A token type associated with the lexeme
	 */
	public final T type;
	
	/**
	 * The starting offset of this lexeme in the original source
	 */
	public final int offset;
	
	/**
	 * The length of the lexeme in the original source
	 */
	public final int length;
	
	/**
	 * The text that the lexeme matched
	 */
	public final String text; // TODO: remove, for testing only

	/**
	 * Lexeme
	 * 
	 * @param type
	 * @param offset
	 * @param length
	 * @param text
	 */
	public Lexeme(T type, int offset, int length, String text) {
		this.type = type;
		this.offset = offset;
		this.length = length;
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return type + "[" + offset + "," + (offset + length) + ") ~" + text + "~";
	}
}
