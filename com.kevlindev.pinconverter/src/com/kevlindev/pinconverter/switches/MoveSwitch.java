/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.switches;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kevlindev.collections.CaselessMap;
import com.kevlindev.pinconverter.ExecutionContext;
import com.kevlindev.pinconverter.commands.ICommand;
import com.kevlindev.pinconverter.model.BusIterator;
import com.kevlindev.utils.StringUtils;

/**
 * MoveSwitch
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class MoveSwitch extends AbstractSwitch {
	/**
	 * A list of move commands
	 */
	private String commandLists;

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
				String[] commands = StringUtils.COMMA_DELIMITER_PATTERN.split(commandLists);
				// TODO: grab translation map from execution context so we
				// append instead of replacing?
				Map<String, String> pinTranslationMap = new CaselessMap<String>();

				for (String command : commands) {
					int index = command.indexOf(StringUtils.TRANSFORM_DELIMITER);
					String sourcePin;
					String destinationPin;

					if (index != -1) {
						sourcePin = command.substring(0, index).trim();
						destinationPin = command.substring(index + StringUtils.TRANSFORM_DELIMITER.length()).trim();
					} else {
						// assume A is a shortcut for A->A
						sourcePin = destinationPin = command.trim();
					}

					BusIterator sourceBus = BusIterator.getBus(sourcePin);
					BusIterator destinationBus = BusIterator.getBus(destinationPin);

					while (sourceBus.hasNext() && destinationBus.hasNext()) {
						pinTranslationMap.put(sourceBus.next(), destinationBus.next());
					}
				}

				context.setPinTranslationMap(pinTranslationMap);

				return true;
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
		return "Move assignments from one wing to another.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "Move Pins";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.papilio.ISwitch#getAliases()
	 */
	@Override
	public List<String> getSwitchNames() {
		List<String> aliases = new ArrayList<String>();

		aliases.add("-mv");
		aliases.add("--move");

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
		boolean result = false;

		if (args.hasNext()) {
			commandLists = args.next();

			result = true;
		}

		return result;
	}
}
