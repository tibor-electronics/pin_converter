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
 * LoadWingSwitch
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class LoadWingSwitch extends AbstractSwitch {
	/**
	 * A full or relative path of a wing definition file
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
				return Registrar.WING_REGISTRY;
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
		return "Load the specified file into the wing registry.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "Load Wings";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.papilio.ISwitch#getAliases()
	 */
	@Override
	public List<String> getSwitchNames() {
		List<String> aliases = new ArrayList<String>();

		aliases.add("-lw");
		aliases.add("--loadWings");

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
