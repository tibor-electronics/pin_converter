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
 * SortSwitch
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class SortSwitch extends AbstractSwitch {
	@Override
	public ICommand createCommand() {
		return new ICommand() {
			@Override
			public boolean execute(ExecutionContext context) {
				context.setIsSorted(true);

				return true;
			}
		};
	}

	@Override
	public String getDescription() {
		return "Sort UCF output by NET name.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "Sort Output";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.AbstractSwitch#getSwitchNames()
	 */
	@Override
	public List<String> getSwitchNames() {
		List<String> names = new ArrayList<String>();

		names.add("-s");
		names.add("--sort");

		return names;
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
