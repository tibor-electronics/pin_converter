/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.commands;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;

import com.kevlindev.logging.Logger;
import com.kevlindev.pinconverter.ExecutionContext;
import com.kevlindev.pinconverter.PinConverter;
import com.kevlindev.utils.StringUtils;

/**
 * TransformCommand
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class TransformCommand implements ICommand {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kevlindev.papilio.ICommand#execute(com.kevlindev.papilio.ExecutionContext
	 * )
	 */
	@Override
	public boolean execute(ExecutionContext context) {
		StringBuilder buffer = new StringBuilder();
		boolean result = false;

		String file = context.getInputFileName();
		Scanner scanner = null;

		try {
			Map<String, String> pinMap = context.getPinMap();

			scanner = new Scanner(new FileReader(file));

			// first use a Scanner to get each line
			while (scanner.hasNextLine()) {
				buffer.append(processLine(scanner.nextLine(), pinMap));
				buffer.append(StringUtils.EOL);
			}

			result = true;
		} catch (FileNotFoundException e) {
			Logger.logError("File not found: " + file);
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}

		context.outputResult(buffer.toString());

		return result;
	}

	/**
	 * Transform the specified line. Lines that do not match the NET-LOC pattern
	 * are left untouched.
	 * 
	 * @param line
	 *            The line to process
	 * @param pinMap
	 *            A map of source board FPGA pin names to destinatin board FPGA
	 *            names
	 * @return A string of a the transformed line or the original line value if
	 *         no transformation took place
	 */
	protected String processLine(String line, Map<String, String> pinMap) {
		Matcher matcher = PinConverter.NET_PATTERN.matcher(line);
		StringBuffer sb = new StringBuffer();

		while (matcher.find()) {
			String headerPinName = matcher.group(2);
			String replacement;

			if (pinMap.containsKey(headerPinName)) {
				replacement = pinMap.get(headerPinName);
			} else {
				replacement = headerPinName;
			}

			matcher.appendReplacement(sb, matcher.group(1) + replacement + matcher.group(3));
		}

		matcher.appendTail(sb);

		return sb.toString();
	}
}
