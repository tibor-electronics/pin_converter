/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.switches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kevlindev.pinconverter.ExecutionContext;
import com.kevlindev.pinconverter.Registrar;
import com.kevlindev.pinconverter.commands.ICommand;
import com.kevlindev.pinconverter.model.Board;
import com.kevlindev.text.Table;
import com.kevlindev.utils.StringUtils;

/**
 * PrintBoardsSwitch
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class ListBoardsSwitch extends AbstractSwitch {
	private static final Set<String> BOARD_WORDS;
	private static final Set<String> WING_WORDS;
	private String target;

	static {
		BOARD_WORDS = new HashSet<String>();
		BOARD_WORDS.add(StringUtils.EMPTY);
		BOARD_WORDS.add("all");
		BOARD_WORDS.add("*");
		BOARD_WORDS.add("board");
		BOARD_WORDS.add("boards");

		WING_WORDS = new HashSet<String>();
		WING_WORDS.add(StringUtils.EMPTY);
		WING_WORDS.add("all");
		WING_WORDS.add("*");
		WING_WORDS.add("wing");
		WING_WORDS.add("wings");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#createCommand()
	 */
	@Override
	public ICommand createCommand() {
		final String lowerTarget = (target != null) ? target.toLowerCase() : StringUtils.EMPTY;

		return new ICommand() {
			@Override
			public boolean execute(ExecutionContext context) {
				List<Board> boards = new ArrayList<Board>();

				if (BOARD_WORDS.contains(lowerTarget)) {
					boards.addAll(Registrar.BOARD_REGISTRY.getBoards());
				}

				if (WING_WORDS.contains(lowerTarget)) {
					boards.addAll(Registrar.WING_REGISTRY.getBoards());
				}

				Collections.sort(boards, new Comparator<Board>() {
					@Override
					public int compare(Board o1, Board o2) {
						String name1 = o1.getNames().get(0);
						String name2 = o2.getNames().get(0);

						return name1.compareToIgnoreCase(name2);
					}
				});

				Table table = new Table();

				table.addHeaders("Name", "Type", "Aliases");

				for (Board board : boards) {
					String type = (board.isWing()) ? "Wing" : "Board";
					List<String> names = new ArrayList<String>(board.getNames());
					String primary = names.remove(0);
					String aliases = StringUtils.join(", ", names);

					table.addRow(primary, type, aliases);
				}

				System.out.println(table);

				return true;
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Return a listing of all loaded boards and their aliases.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "List Boards";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#getAliases()
	 */
	@Override
	public List<String> getSwitchNames() {
		List<String> aliases = new ArrayList<String>();

		aliases.add("-l");
		aliases.add("--listBoards");

		return aliases;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kevlindev.pinconverter.switches.ISwitch#processArg(com.kevlindev.
	 * pinconverter.PinConverter, java.util.Iterator)
	 */
	@Override
	public boolean processArg(Iterator<String> args) {
		boolean result = false;

		if (args.hasNext()) {
			target = args.next();

			result = true;
		}

		return result;
	}
}
