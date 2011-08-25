/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.logging;

import java.util.ArrayList;
import java.util.List;

/**
 * Logger
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class Logger {
	/**
	 * This is the singleton instance of the Logger
	 */
	private static Logger INSTANCE;

	/**
	 * getInstance
	 * 
	 * @return Logger
	 */
	public static Logger getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Logger();
		}

		return INSTANCE;
	}

	/**
	 * logError
	 * 
	 * @param message
	 */
	public static void logError(String message) {
		Logger logger = getInstance();

		if (logger.logListeners != null) {
			for (ILogListener listener : logger.logListeners) {
				listener.logError(message);
			}
		}
	}

	/**
	 * logInfo
	 * 
	 * @param message
	 */
	public static void logInfo(String message) {
		Logger logger = getInstance();

		if (logger.logListeners != null) {
			for (ILogListener listener : logger.logListeners) {
				listener.logInfo(message);
			}
		}
	}

	/**
	 * logWarning
	 * 
	 * @param message
	 */
	public static void logWarning(String message) {
		Logger logger = getInstance();

		if (logger.logListeners != null) {
			for (ILogListener listener : logger.logListeners) {
				listener.logWarning(message);
			}
		}
	}

	/**
	 * trace
	 * 
	 * @param message
	 */
	public static void trace(String message) {
		Logger logger = getInstance();

		if (logger.logListeners != null) {
			for (ILogListener listener : logger.logListeners) {
				listener.trace(message);
			}
		}
	}

	/**
	 * This is a list of log listeners that will be fired when a log event comes
	 * in
	 */
	private List<ILogListener> logListeners;

	/**
	 * Logger
	 */
	private Logger() {
	}

	/**
	 * addLogListener
	 * 
	 * @param listener
	 */
	public void addLogListener(ILogListener listener) {
		if (listener != null) {
			if (logListeners == null) {
				logListeners = new ArrayList<ILogListener>();
			}

			logListeners.add(listener);
		}
	}

	/**
	 * removeLogListener
	 * 
	 * @param listener
	 */
	public void removeLogListener(ILogListener listener) {
		if (listener != null && logListeners != null) {
			logListeners.remove(listener);
		}
	}
}
