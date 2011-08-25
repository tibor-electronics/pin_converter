/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.kevlindev.logging.Logger;
import com.kevlindev.pinconverter.ExecutionContext;
import com.kevlindev.pinconverter.BoardRegistry;

/**
 * AbstractLoadCommand
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public abstract class AbstractLoadCommand implements ICommand { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.preferInterfacesToAbstractClasses
	/**
	 * The full or relative path of the file to load
	 */
	private String filename;

	/**
	 * AbstractLoadCommand
	 * 
	 * @param filename
	 */
	public AbstractLoadCommand(String filename) {
		this.filename = filename;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.commands.ICommand#execute(com.kevlindev.pinconverter.ExecutionContext)
	 */
	@Override
	public boolean execute(ExecutionContext context) {
		InputStream input = getInputStream();
		boolean result = false;

		if (input != null) {
			BoardRegistry registry = getRegistry();

			result = registry.load(input);
		}

		return result;
	}

	/**
	 * getInputStream
	 * 
	 * @return InputStream
	 */
	protected InputStream getInputStream() {
		File file = new File(filename);
		InputStream result = null;

		if (file.exists()) {
			if (file.canRead()) {
				try {
					result = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				Logger.logError("Board definition file is not readable: '" + filename + "'");
			}
		} else {
			Logger.logError("Board definition file does not exist: '" + filename + "'");
		}

		return result;
	}

	/**
	 * getRegistry
	 * 
	 * @return PinRegistry
	 */
	protected abstract BoardRegistry getRegistry();
}
