/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.parsing;

import java.util.regex.Matcher;

/**
 * ITokenMatcher
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public interface ITokenMatcher {
	/**
	 * getMatcher
	 * 
	 * @param source
	 * @return Matcher
	 */
	Matcher getMatcher(String source);
}
