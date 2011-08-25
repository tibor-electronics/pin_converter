/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kevlindev.collections.CaselessMap;
import com.kevlindev.pinconverter.model.Board;
import com.kevlindev.pinconverter.parsing.BoardDefinitionParser;
import com.kevlindev.utils.IOUtils;

/**
 * PinRegistry
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class BoardRegistry {
	/**
	 * A map of boards by their case-insensitive names
	 */
	private Map<String, Board> boardsByName;

	/**
	 * addBoard
	 * 
	 * @param board
	 */
	public void addBoard(Board board) {
		if (board != null) {
			if (boardsByName == null) {
				boardsByName = new CaselessMap<Board>();
			}

			for (String name : board.getNames()) {
				boardsByName.put(name, board);
			}
		}
	}

	/**
	 * Remove all board entries from the registry
	 */
	public void clear() {
		if (boardsByName != null) {
			boardsByName.clear();
		}
	}

	/**
	 * Determines if this registry contains a pin map for the specified board
	 * 
	 * @param boardName
	 *            The name of the board to test
	 * @return Returns true if the board exists in the registry
	 */
	public boolean contains(String boardName) {
		return (boardsByName != null && boardsByName.containsKey(boardName));
	}

	/**
	 * Return the pin name map for the specified board.
	 * 
	 * @param boardName
	 *            The name of the board in which we're interested
	 * @return A Map<String,String> which maps a header pin name to an FPGA pin
	 *         name. If the board does not exist in the registry, then null is
	 *         returned
	 */
	public Board getBoard(String boardName) {
		return boardsByName.get(boardName);
	}

	/**
	 * getBoards
	 * 
	 * @return List<Board>
	 */
	public List<Board> getBoards() {
		List<Board> result = Collections.emptyList();

		if (boardsByName != null) {
			result = new ArrayList<Board>();

			Set<String> ignore = new HashSet<String>();

			for (Board board : boardsByName.values()) {
				String primary = board.getNames().get(0);

				if (!ignore.contains(primary)) {
					result.add(board);
					ignore.add(primary);
				}
			}
		}

		return result;
	}

	/**
	 * load
	 * 
	 * @param input
	 * @return boolean
	 */
	public boolean load(InputStream input) {
		boolean result = false;

		String source = IOUtils.getString(input);
		BoardDefinitionParser parser = new BoardDefinitionParser();

		parser.parse(source);

		List<Board> boards = parser.getBoards();

		if (boards != null) {
			for (Board board : boards) {
				addBoard(board);
			}

			result = true;
		}

		return result;
	}
}
