/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter;

/**
 * Registrar
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class Registrar {
	/**
	 * A registry of all boards
	 */
	public static final BoardRegistry BOARD_REGISTRY = new BoardRegistry();

	/**
	 * A registry of all wings
	 */
	public static final BoardRegistry WING_REGISTRY = new WingRegistry();

	/**
	 * A private constructor so this class can be instantiated only within the
	 * class
	 */
	private Registrar() {
	}
}
