/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.switches;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import com.kevlindev.pinconverter.commands.ICommand;
import com.kevlindev.utils.IOUtils;
import com.kevlindev.utils.StringUtils;

/**
 * AbstractSwitch
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public abstract class AbstractSwitch implements ISwitch {
	private static final String RESOURCES = "/com/kevlindev/pinconverter/resources/";

	/**
	 * A base class for all switches. This class cannot be instantiated publicly
	 */
	protected AbstractSwitch() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#createCommand()
	 */
	@Override
	public ICommand createCommand() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#getDescription()
	 */
	@Override
	public String getDescription() {
		return StringUtils.EMPTY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#getLongDescription()
	 */
	@Override
	public String getLongDescription() {
		String simpleName = this.getClass().getSimpleName();
		String fileName = RESOURCES + simpleName + ".txt";
		InputStream is = this.getClass().getResourceAsStream(fileName);
		String result = StringUtils.EMPTY;

		if (is != null) {
			result = IOUtils.getString(is);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#getAliases()
	 */
	@Override
	public abstract List<String> getSwitchNames();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kevlindev.pinconverter.switches.ISwitch#processArg(com.kevlindev.
	 * pinconverter.PinConverter, java.util.Iterator)
	 */
	@Override
	public abstract boolean processArg(Iterator<String> args);
}
