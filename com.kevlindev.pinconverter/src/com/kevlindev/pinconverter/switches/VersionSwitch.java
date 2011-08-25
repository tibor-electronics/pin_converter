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
 * VersionSwitch
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class VersionSwitch extends AbstractSwitch {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.AbstractSwitch#createCommand()
	 */
	@Override
	public ICommand createCommand() {
		return new ICommand() {
			@Override
			public boolean execute(ExecutionContext context) {
				// TODO: version info should be retrieved from a build artifact
				System.out.println("PinConverter v0.1.0 alpha");
				return true;
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.AbstractSwitch#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Return the version info for this tool.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "Version Info";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.AbstractSwitch#getAliases()
	 */
	@Override
	public List<String> getSwitchNames() {
		List<String> aliases = new ArrayList<String>();

		aliases.add("-v");
		aliases.add("--version");

		return aliases;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kevlindev.pinconverter.switches.AbstractSwitch#processArg(java.util
	 * .Iterator)
	 */
	@Override
	public boolean processArg(Iterator<String> args) {
		return true;
	}
}
