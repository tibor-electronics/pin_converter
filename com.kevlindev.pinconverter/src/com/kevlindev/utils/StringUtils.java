/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * StringUtils
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class StringUtils {
	/**
	 * A pattern used to split comma-delimited lists
	 */
	public static final Pattern COMMA_DELIMITER_PATTERN = Pattern.compile("\\s*,\\s*");

	/**
	 * A pattern used to split semicolon-delimited lists
	 */
	public static final Pattern SEMICOLON_DELIMITER_PATTERN = Pattern.compile("\\s*;\\s*");

	/**
	 * A pattern used to recognize and split on EOL characters
	 */
	public static final Pattern EOL_PATTERN = Pattern.compile("\r|\r\n|\n");

	/**
	 * The empty string
	 */
	public static final String EMPTY = "";

	/**
	 * The OS-specific line ending string
	 */
	public static final String EOL = System.getProperty("line.separator", "\n");

	/**
	 * The delimiter used between source and destination items in a
	 * transformation expression
	 */
	public static final String TRANSFORM_DELIMITER = "->";

	/**
	 * A utility method used to find the end of a line in a string using a
	 * specified offset
	 * 
	 * @param text
	 *            The text to search
	 * @param startingOffset
	 *            The offset where the search begins
	 * @return int The offset of the line terminator. If the terminator is \r\n,
	 *         the offset will point to \r
	 */
	public static int findEndOfLine(String text, int startingOffset) {
		int end = -1;

		if (text != null) {
			startingOffset = Math.max(startingOffset, 0);
			end = startingOffset;

			while (end < text.length()) {
				char c = text.charAt(end);

				// FIXME: If we started on a \n character, we should look back
				// one character for \r
				if (c == '\r' || c == '\n') {
					break;
				}

				end++;
			}
		}

		return end;
	}

	/**
	 * A utility method used to find the start of a line in a string using a
	 * specified offset
	 * 
	 * @param text
	 *            The text to search
	 * @param startingOffset
	 *            The offset where the search begins
	 * @return int The offset of the first character in the line.
	 */
	public static int findStartOfLine(String text, int startingOffset) {
		int offset = -1;

		if (text != null) {
			// possibly truncate starting offset so we can property detect if
			// we're starting on a \n or \r character later
			startingOffset = Math.min(startingOffset, text.length() - 1);
			offset = startingOffset;

			while (offset > 0) {
				char c = text.charAt(offset);

				// FIXME: This does not properly handle the case where
				// starting offset is pointing to the \n of \r\n
				if (c == '\r' || c == '\n') {
					if (offset != startingOffset) {
						offset++;
						break;
					}
				}

				offset--;
			}
		}

		return offset;
	}

	/**
	 * Join one or more items placing the specified delimiter between each item
	 * 
	 * @param delimiter
	 *            The delimiter to place between each item. This value may be
	 *            null, in which case, the empty string will be the delimiter
	 * @param items
	 *            The items to join. This value may be null, in which case, the
	 *            empty string will be returned
	 * @return A single string that is the combination of the items and the
	 *         delimiters
	 */
	public static String join(String delimiter, Collection<String> items) {
		StringBuilder buffer = new StringBuilder();

		if (delimiter == null) {
			delimiter = "";
		}

		if (items != null) {
			for (String item : items) {
				if (item != null) {
					buffer.append(item);
				}

				buffer.append(delimiter);
			}

			// trim off extra delimiter
			if (buffer.length() > 0) {
				buffer.setLength(buffer.length() - delimiter.length());
			}
		}

		return buffer.toString();
	}

	/**
	 * Join one or more items placing the specified delimiter between each item
	 * 
	 * @param delimiter
	 *            The delimiter to place between each item. This value may be
	 *            null, in which case, the empty string will be the delimiter
	 * @param items
	 *            The items to join. This value may be null, in which case, the
	 *            empty string will be returned
	 * @return A single string that is the combination of the items and the
	 *         delimiters
	 */
	public static String join(String delimiter, String... items) {
		String result;

		if (items != null) {
			result = join(delimiter, Arrays.asList(items));
		} else {
			result = EMPTY;
		}

		return result;
	}

	/**
	 * repeatString
	 * 
	 * @param string
	 * @param count
	 * @return String
	 */
	public static String repeatString(String string, int count) {
		return (count > 0) ? join(string, new String[count + 1]) : EMPTY;
	}

	/**
	 * trimLines
	 * 
	 * @param text
	 * @return String
	 */
	public static String trimLines(String text) {
		String result = null;

		if (text != null) {
			String[] lines = StringUtils.EOL_PATTERN.split(text);

			for (int i = 0; i < lines.length; i++) {
				lines[i] = lines[i].trim();
			}

			result = StringUtils.join(StringUtils.EOL, lines);
		}

		return result;
	}

	/**
	 * wrap
	 * 
	 * @param text
	 * @param width
	 * @return List<String>
	 */
	public static List<String> wrap(String text, int width) {
		List<String> result = new ArrayList<String>();

		if (text != null) {
			text = text.trim().replace("\\s+", " ");

			for (String line : StringUtils.EOL_PATTERN.split(text)) {
				if (line.length() > 0 && width > 0) {
					while (line.length() != 0) {
						int index = 0;
						int candidate = line.indexOf(' ');

						while (candidate != -1 && candidate <= width) {
							index = candidate;
							candidate = line.indexOf(' ', index + 1);
						}

						if (candidate == -1 && line.length() <= width) {
							index = 0;
						}

						int nextStart;

						if (index == 0) {
							index = Math.min(line.length(), width);
							nextStart = index;
						} else {
							nextStart = index + 1;
						}

						result.add(line.substring(0, index));
						line = line.substring(nextStart);
					}
				} else {
					result.add(line);
				}
			}
		}

		return result;
	}

	/**
	 * Make sure on the class itself can instantiate this class
	 */
	private StringUtils() {
	}
}
