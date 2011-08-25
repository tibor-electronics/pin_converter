/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.model;

/**
 * Configuration
 */
public enum Configuration {
	/**
	 * A flag indicating if the output should be sorted
	 */
	SORTED,

	/**
	 * A flag indicating if the UCF output should render "additions" as a
	 * left-justified column or if it should rendered without alignment causing
	 * following columns to shift left to remove all extra space.
	 */
	COLLAPSE_COLUMNS,

	/**
	 * A flag indicating if the output should be sorted
	 */
	EXCLUDE_UNUSED_PINS,

	/**
	 * A flag indicating that space around '=' characters should be added
	 */
	SPACE_AROUND_EQUALS
}
