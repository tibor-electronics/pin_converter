/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter;

import com.kevlindev.pinconverter.model.Board;

/**
 * WingRegistry
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class WingRegistry extends BoardRegistry {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kevlindev.pinconverter.BoardRegistry#addBoard(com.kevlindev.pinconverter
	 * .model.Board)
	 */
	@Override
	public void addBoard(Board board) {
		super.addBoard(board);

		board.setIsWing(true);
	}
}
