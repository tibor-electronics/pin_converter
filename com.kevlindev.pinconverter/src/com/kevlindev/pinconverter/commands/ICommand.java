/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.commands;

import com.kevlindev.pinconverter.ExecutionContext;

/**
 * ICommand
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public interface ICommand {
	/**
	 * Execute the command, returning a boolean success value.
	 * 
	 * @param context
	 *            Context information that may be useful during execution.
	 *            ICommands both store and retrieve information from this
	 *            context
	 * @return A boolean value indication success, true, or failure, false
	 */
	boolean execute(ExecutionContext context);
}
