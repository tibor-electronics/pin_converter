/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.switches;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kevlindev.pinconverter.ExecutionContext;
import com.kevlindev.pinconverter.commands.ICommand;
import com.kevlindev.utils.StringUtils;

/**
 * PlaceWingSwitch
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class PlaceWingSwitch extends AbstractSwitch {
	/**
	 * A list of wings and the bus to which they should be assigned
	 */
	private String wingPlacementList;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#createCommand()
	 */
	@Override
	public ICommand createCommand() {
		return new ICommand() {
			@Override
			public boolean execute(ExecutionContext context) {
				String[] wingPlacements = StringUtils.SEMICOLON_DELIMITER_PATTERN.split(wingPlacementList);

				for (String wingPlacement : wingPlacements) {
					int index = wingPlacement.indexOf(StringUtils.TRANSFORM_DELIMITER);

					if (index != -1) {
						String wingName = wingPlacement.substring(0, index).trim();
						String destinationPinList = wingPlacement.substring(index + StringUtils.TRANSFORM_DELIMITER.length()).trim();
						String[] destinationPins = StringUtils.COMMA_DELIMITER_PATTERN.split(destinationPinList);

						for (String destinationPin : destinationPins) {
							if (destinationPin.endsWith("l") || destinationPin.endsWith("L")) {
								destinationPin = destinationPin.substring(0, destinationPin.length() - 1) + "0"; // $codepro.audit.disable
																													// stringConcatenationInLoop
							} else if (destinationPin.endsWith("h") || destinationPin.endsWith("H")) {
								destinationPin = destinationPin.substring(0, destinationPin.length() - 1) + "8"; // $codepro.audit.disable
																													// stringConcatenationInLoop
							} else {
								destinationPin = destinationPin + "0"; // $codepro.audit.disable
																		// stringConcatenationInLoop
							}

							context.addWingPlacement(destinationPin, wingName);
						}
					}
				}

				return true;
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#getDescription()
	 */
	@Override
	public String getDescription() {
		return "When generating a UCF this will place a wing at a specified location.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "Place Wing";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#getAliases()
	 */
	@Override
	public List<String> getSwitchNames() {
		List<String> aliases = new ArrayList<String>();

		aliases.add("-pw");
		aliases.add("--placeWing");

		return aliases;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kevlindev.pinconverter.switches.ISwitch#processArg(com.kevlindev.
	 * pinconverter.PinConverter, java.util.Iterator)
	 */
	@Override
	public boolean processArg(Iterator<String> args) {
		boolean result = false;

		if (args.hasNext()) {
			wingPlacementList = args.next();

			result = true;
		}

		return result;
	}
}
