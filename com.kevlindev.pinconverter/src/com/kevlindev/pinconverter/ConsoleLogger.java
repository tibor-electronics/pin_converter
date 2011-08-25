/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter;

import com.kevlindev.logging.ILogListener;

/**
 * ConsoleLogger
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class ConsoleLogger implements ILogListener {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.logging.ILogListener#logError(java.lang.String)
	 */
	@Override
	public void logError(String message) {
		System.err.println(message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.logging.ILogListener#logInfo(java.lang.String)
	 */
	@Override
	public void logInfo(String message) {
		System.out.println("Info: " + message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.logging.ILogListener#logWarning(java.lang.String)
	 */
	@Override
	public void logWarning(String message) {
		System.out.println("Warning: " + message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.logging.ILogListener#trace(java.lang.String)
	 */
	@Override
	public void trace(String message) {
		System.out.println("Trace: " + message);
	}
}
