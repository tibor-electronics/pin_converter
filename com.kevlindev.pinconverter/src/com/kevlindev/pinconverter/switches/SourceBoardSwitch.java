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

/**
 * SourceBoardSwitch
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class SourceBoardSwitch extends AbstractSwitch {
	/**
	 * The name of the board to use for input
	 */
	private String sourceBoard;

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
				context.setSourceBoardName(sourceBoard);

				return true;
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.papilio.ICommand#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Set the board type for the source file.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "Set Source";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.papilio.ICommand#getAliases()
	 */
	@Override
	public List<String> getSwitchNames() {
		List<String> aliases = new ArrayList<String>();

		aliases.add("-src");
		aliases.add("--sourceBoard");

		return aliases;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kevlindev.pinconverter.switches.ISwitch#processArg(com.kevlindev.
	 * pinconverter.PinConverter, java.util.Iterator)
	 */
	public boolean processArg(Iterator<String> args) {
		boolean result = false;

		if (args.hasNext()) {
			sourceBoard = args.next();

			result = true;
		}

		return result;
	}
}
