/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.logging;

/**
 * ILogListener
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public interface ILogListener {
	/**
	 * logError
	 * 
	 * @param message
	 */
	void logError(String message);

	/**
	 * logInfo
	 * 
	 * @param message
	 */
	void logInfo(String message);

	/**
	 * logWarning
	 * 
	 * @param message
	 */
	void logWarning(String message);

	/**
	 * trace
	 * 
	 * @param message
	 */
	void trace(String message);
}
