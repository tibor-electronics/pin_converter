/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.model;

import java.util.Iterator;

/**
 * BusIterator
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class BusIterator implements Iterator<String> {
	/**
	 * Given a string using bus range syntax, create a new iterator that walks
	 * all pins in a bus starting from the first number up to or down to the
	 * second number.
	 * 
	 * @param text
	 * @return BusIterator
	 */
	public static BusIterator getBus(String text) {
		BusIterator result = null;
		int lbracket = text.indexOf('[');

		if (lbracket != -1) {
			String name = text.substring(0, lbracket).trim();
			int rbracket = text.indexOf(']', lbracket);

			if (rbracket != -1) {
				String ranges = text.substring(lbracket + 1, rbracket).trim();
				int colon = ranges.indexOf(':');

				if (colon != -1) {
					String startString = ranges.substring(0, colon).trim();
					String endString = ranges.substring(colon + 1).trim();
					int start = Integer.parseInt(startString);
					int end = Integer.parseInt(endString);

					result = new BusIterator(name, start, end);
				}
			}
		} else if (text.toUpperCase().endsWith("L")) {
			result = new BusIterator(text.substring(0, text.length() - 1), 0, 7);
		} else if (text.toUpperCase().endsWith("H")) {
			result = new BusIterator(text.substring(0, text.length() - 1), 8, 15);
		}

		if (result == null) {
			result = new BusIterator(text);
		}

		return result;
	}

	/**
	 * The base name for the bus
	 */
	private String name;

	/**
	 * The starting index within the bus. This value may be larger than the
	 * ending value.
	 */
	private int start;

	/**
	 * The ending index within the bus. This value may be smaller than the
	 * starting value.
	 */
	private int end;

	/**
	 * The value to add to the current value. Either 1 or -1
	 */
	private int step;

	/**
	 * The current value of this iterator
	 */
	private int current;

	/**
	 * Create a new bus for the specified name. The start and end ranges default
	 * to 0 and 15, respectively
	 * 
	 * @param name
	 *            The name of the bus
	 */
	public BusIterator(String name) {
		this(name, 0, 15);
	}

	/**
	 * Create a new bus for the specified name and specified range. Note that
	 * start may be less than, equal to, or greater than end
	 * 
	 * @param name
	 *            The name of the bus
	 * @param start
	 *            The starting index on the bus
	 * @param end
	 *            The ending index on the bus
	 */
	public BusIterator(String name, int start, int end) {
		this.name = name;
		this.start = start;
		this.end = end;

		step = (start <= end) ? 1 : -1;
		current = start;
	}

	/**
	 * getName
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return current != end + step;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#next()
	 */
	@Override
	public String next() {
		String result = name + current;

		current += step;

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name + "[" + start + ":" + end + "]";
	}
}