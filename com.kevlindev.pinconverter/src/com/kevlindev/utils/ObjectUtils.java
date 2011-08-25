/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.utils;

/**
 * ObjectUtils
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class ObjectUtils {
	/**
	 * A simple quality helper method that takes null values into account. This
	 * method assumes that two nulls are equal
	 * 
	 * @param o1
	 *            The first object to compare. If o1 and o2 are not negative,
	 *            then o1's equals method will be called passing in o2 as its
	 *            argument
	 * @param o2
	 *            The second object to compare.
	 * @return boolean Returns true if the two items are considered to be equal
	 */
	public static boolean equal(Object o1, Object o2) {
		if (o1 == null) {
			return o2 == null;
		} else {
			return o1.equals(o2);
		}
	}

	/**
	 * ObjectUtils
	 */
	private ObjectUtils() {
	}
}
