/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.switches;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kevlindev.pinconverter.BoardRegistry;
import com.kevlindev.pinconverter.Registrar;
import com.kevlindev.pinconverter.commands.AbstractLoadCommand;
import com.kevlindev.pinconverter.commands.ICommand;

/**
 * LoadBoardSwitch
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class LoadBoardSwitch extends AbstractSwitch {
	/**
	 * A full or relative path of a board definition file
	 */
	private String filename;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.papilio.ISwitch#createCommand()
	 */
	@Override
	public ICommand createCommand() {
		return new AbstractLoadCommand(filename) {
			@Override
			protected BoardRegistry getRegistry() {
				return Registrar.BOARD_REGISTRY;
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
		return "Load the specified file into the board registry.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "Load Boards";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.papilio.ISwitch#getAliases()
	 */
	@Override
	public List<String> getSwitchNames() {
		List<String> aliases = new ArrayList<String>();

		aliases.add("-lb");
		aliases.add("--loadBoards");

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
			filename = args.next();

			result = true;
		}

		return result;
	}
}
