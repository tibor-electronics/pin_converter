/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.switches;

import java.util.Iterator;
import java.util.List;

import com.kevlindev.pinconverter.commands.ICommand;

/**
 * ISwitch
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public interface ISwitch {
	/**
	 * Create a new command that implements the behavior described by this
	 * switch and its arguments. Switches may return null even if the were
	 * processed successfully
	 * 
	 * @return The ICommand to implement this switches behavior. This value may
	 *         be null
	 */
	ICommand createCommand();

	/**
	 * Get a short description for what this switch is used. This should fit
	 * comfortable on one line.
	 * 
	 * @return A one-line description of this switch
	 */
	String getDescription();

	/**
	 * getDisplayName
	 * 
	 * @return String
	 */
	String getDisplayName();

	/**
	 * Get a long description of what this switch does. This text most likely
	 * will be wrapped and should go into good detail.
	 * 
	 * @return A multi-line description of this switch
	 */
	String getLongDescription();
	
	/**
	 * Get a list of alternate names that can be used on the command-line for
	 * this switch. Typically, these are long version switch names, but that is
	 * not a requirement
	 * 
	 * @return A list of alternate switch names. This value may be null.
	 */
	List<String> getSwitchNames();

	/**
	 * Process this command-line argument and possible arguments following it
	 * which serve as parameters to this switch
	 * @param args
	 *            The argument iterator currently positioned on the switch name
	 * 
	 * @return Returns success, true, or failure, false, if the switch and its
	 *         argument were processed successfully or not
	 */
	boolean processArg(Iterator<String> args);
}
