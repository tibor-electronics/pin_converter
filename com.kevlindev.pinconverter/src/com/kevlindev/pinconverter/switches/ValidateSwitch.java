/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.switches;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;

import com.kevlindev.logging.Logger;
import com.kevlindev.pinconverter.ExecutionContext;
import com.kevlindev.pinconverter.PinConverter;
import com.kevlindev.pinconverter.commands.ICommand;
import com.kevlindev.pinconverter.model.Board;
import com.kevlindev.pinconverter.model.Pin;

/**
 * VerifySwitch
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class ValidateSwitch extends AbstractSwitch {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.papilio.ISwitch#createCommand()
	 */
	@Override
	public ICommand createCommand() {
		return new ICommand() {
			@Override
			public boolean execute(ExecutionContext context) {
				boolean result = false;

				// TODO: This code is largely common to TransformCommand.
				// Refactor for better reuse
				Board board = context.getSourceBoard();

				// Create a set of FPGA pin names
				Set<String> fpgaPins = new HashSet<String>();

				for (Pin pin : board.getPins()) {
					fpgaPins.add(pin.getName());
				}

				String file = context.getInputFileName();
				Scanner scanner = null;

				try {
					boolean sawFalse = false;
					scanner = new Scanner(new FileReader(file));

					// first use a Scanner to get each line
					while (scanner.hasNextLine()) {
						if (!processLine(scanner.nextLine(), fpgaPins)) {
							sawFalse = true;
						}
					}

					result = !sawFalse;
				} catch (FileNotFoundException e) {
					Logger.logError("File not found: " + file);
				} finally {
					if (scanner != null) {
						scanner.close();
					}
				}

				if (result) {
					// @formatter:off
					String message = MessageFormat.format(
						"All pins in file ''{0}'' are valid for the {1} board",
						context.getInputFileName(),
						context.getSourceBoardName()
					);
					// @formatter:on

					context.outputResult(message);
				}

				return result;
			}

			private boolean processLine(String line, Set<String> pins) {
				Matcher matcher = PinConverter.NET_PATTERN.matcher(line);
				boolean result = true;

				if (matcher.matches()) {
					String fpgaPin = matcher.group(2);

					if (!pins.contains(matcher.group(2))) {
						// @formatter:off
						String message = MessageFormat.format(
							"Invalid FPGA pin ''{0}'': {1}",
							fpgaPin,
							line
						);
						// @formatter:on

						Logger.logError(message);

						result = false;
					}
				}

				return result;
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.papilio.ISwitch#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Verify that a UCF file is using valid FPGA pin names.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "Validate Board";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.papilio.ISwitch#getAliases()
	 */
	@Override
	public List<String> getSwitchNames() {
		List<String> aliases = new ArrayList<String>();

		aliases.add("-val");
		aliases.add("--validate");
		aliases.add("--verify");

		return aliases;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kevlindev.papilio.ISwitch#processArg(com.kevlindev.papilio.PinConverter
	 * , java.util.Iterator)
	 */
	@Override
	public boolean processArg(Iterator<String> args) {
		return true;
	}
}
