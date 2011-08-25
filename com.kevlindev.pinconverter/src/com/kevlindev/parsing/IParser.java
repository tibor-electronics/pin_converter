/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.parsing;

/**
 * IParser
 * 
 * @author Kevin Lindsey
 * @version 1.0
 * @param <T>
 *            any type of enumeration
 */
public interface IParser<T extends Enum<T>> {
	/**
	 * parse
	 * 
	 * @param source
	 */
	void parse(String source);
}
