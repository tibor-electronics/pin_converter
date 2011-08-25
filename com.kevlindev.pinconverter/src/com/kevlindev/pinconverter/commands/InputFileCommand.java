/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.commands;

import com.kevlindev.pinconverter.ExecutionContext;

/**
 * InputFileCommand
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class InputFileCommand implements ICommand {
	/**
	 * The full or relative path of the file to input
	 */
	private String fileName;

	/**
	 * InputFileCommand
	 * 
	 * @param fileName
	 */
	public InputFileCommand(String fileName) {
		this.fileName = fileName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kevlindev.papilio.ICommand#execute(com.kevlindev.papilio.ExecutionContext
	 * )
	 */
	@Override
	public boolean execute(ExecutionContext context) {
		context.setInputFileName(fileName);

		return true;
	}
}
