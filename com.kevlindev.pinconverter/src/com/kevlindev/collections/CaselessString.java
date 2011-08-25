/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.collections;

import java.util.Locale;

/**
 * CaselessString
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class CaselessString {
	/**
	 * This is the original string value
	 */
	private String value;

	/**
	 * This is a lowercase (canonicalized) version of the original string value
	 */
	private String lowerValue;

	/**
	 * Create a new caseless string
	 * 
	 * @param value
	 *            The string value of this caseless string
	 */
	public CaselessString(String value) {
		this.value = value;

		lowerValue = value.toLowerCase(Locale.getDefault());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;

		if (this == obj) {
			result = true;
		} else if (obj instanceof CaselessString) {
			result = lowerValue.equals(((CaselessString) obj).lowerValue);
		} else if (obj instanceof String) {
			result = lowerValue.equals(((String) obj).toLowerCase());
		} else {
			super.equals(obj);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return lowerValue.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return value;
	}
}
