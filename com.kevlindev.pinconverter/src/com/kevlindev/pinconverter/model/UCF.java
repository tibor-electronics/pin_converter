/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.model;

/**
 * UCF
 */
public enum UCF {
	/**
	 * The column in a UCF file containing the NET assignment
	 */
	NET,

	/**
	 * The column in a UCF file containing the LOC assignment
	 */
	LOC,

	/**
	 * The column in a UCF file containing any additional constraints
	 */
	ADDITIONS,

	/**
	 * The last column in a UCF file containing additional commentary about the
	 * given entry
	 */
	COMMENT;
}
