/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.collections;

/**
 * Tuple
 */
public class Tuple<T, U> {
	public final T first;
	public final U last;

	/**
	 * Tuple
	 * 
	 * @param first
	 * @param last
	 */
	public Tuple(T first, U last) {
		this.first = first;
		this.last = last;
	}
}
