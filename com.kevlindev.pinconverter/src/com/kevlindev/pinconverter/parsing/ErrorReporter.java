/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.parsing;

import java.text.MessageFormat;

import com.kevlindev.utils.StringUtils;

/**
 * ErrorReporter
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class ErrorReporter {
	/**
	 * The entire source of the text being reported on
	 */
	private String source;

	/**
	 * The offset where the error occurred
	 */
	private int offset;

	/**
	 * The beginning of the line where the error occurred
	 */
	private int lineStart;

	/**
	 * The end of the line where the error occurred
	 */
	private int lineEnd;

	/**
	 * ErrorReporter
	 * 
	 * @param source
	 * @param offset
	 */
	public ErrorReporter(String source, int offset) {
		this.source = source;
		this.offset = offset;

		lineStart = StringUtils.findStartOfLine(source, offset);
		lineEnd = StringUtils.findEndOfLine(source, offset);
	}

	/**
	 * getErrorMessage
	 * 
	 * @param message
	 * @return String
	 */
	public String getErrorMessage(String message) {
		// @formatter:off
		return MessageFormat.format(
			"At offset {1}: {2}{0}{3}{0}{4}",
			StringUtils.EOL,
			offset,
			message,
			getLine(),
			getMarkerLine()
		);
		// @formatter:on;
	}

	/**
	 * getLine
	 * 
	 * @return String
	 */
	private String getLine() {
		return source.substring(lineStart, lineEnd);
	}

	/**
	 * getMarkerLine
	 * 
	 * @return String
	 */
	private String getMarkerLine() {
		StringBuilder buffer = new StringBuilder();
		boolean inWhitespace = true;

		for (int i = lineStart; i < lineEnd; i++) {
			if (i == offset) {
				buffer.append('^');
			} else {
				if (inWhitespace) {
					char c = source.charAt(i);

					if (Character.isWhitespace(c)) {
						buffer.append(c);
					} else {
						buffer.append('-');
						inWhitespace = false;
					}
				} else {
					buffer.append('-');
				}
			}
		}

		return buffer.toString();
	}
}
