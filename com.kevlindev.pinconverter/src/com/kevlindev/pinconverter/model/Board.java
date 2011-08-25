/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kevlindev.collections.CaselessSet;
import com.kevlindev.collections.CaselessString;
import com.kevlindev.collections.Tuple;
import com.kevlindev.pinconverter.Registrar;
import com.kevlindev.text.Table;
import com.kevlindev.utils.StringUtils;

/**
 * Board
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class Board implements Iterable<Bus> {
	/**
	 * A list of names and aliases for this board
	 */
	private List<String> names;

	/**
	 * A list of buses
	 */
	private List<Bus> buses;

	/**
	 * A flag indicating if this board is a wing board
	 */
	private boolean isWing;

	/**
	 * The name of the board from which this board inherits.
	 */
	private String parentBoard;

	/**
	 * A header to associate with this board during UCF generation
	 */
	private String header;

	/**
	 * A collection of configurations to be emitted during UCF generation
	 */
	private List<Tuple<String, String>> configs;

	/**
	 * addBus
	 * 
	 * @param bus
	 */
	public void addBus(Bus bus) {
		if (bus != null) {
			if (buses == null) {
				buses = new ArrayList<Bus>();
			}

			buses.add(bus);

			bus.setOwningBoard(this);
		}
	}

	/**
	 * addName
	 * 
	 * @param name
	 */
	public void addName(String name) {
		if (name != null && name.length() > 0) {
			if (names == null) {
				names = new ArrayList<String>();
			}

			names.add(name);
		}
	}

	/**
	 * containsBusName
	 * 
	 * @param busName
	 * @return boolean
	 */
	public boolean containsBusName(String busName) {
		boolean result = false;

		for (Board board : getInheritanceList()) {
			if (board.getBus(busName) != null) {
				result = true;
				break;
			}
		}

		return result;
	}

	/**
	 * getBus
	 * 
	 * @param index
	 * @return Bus
	 */
	public Bus getBus(int index) {
		Bus result = null;

		for (Board board : getInheritanceList()) {
			int busCount = (board.buses != null) ? board.buses.size() : 0;

			if (index < busCount) {
				result = board.buses.get(index);
				break;
			} else {
				index -= busCount;
			}
		}

		return result;
	}

	/**
	 * getBus
	 * 
	 * @param busName
	 * @return Bus
	 */
	public Bus getBus(String busName) {
		CaselessString caselessName = new CaselessString(busName);
		Bus result = null;

		for (Board board : getInheritanceList()) {
			if (board.buses != null) {
				for (Bus bus : board.buses) {
					if (caselessName.equals(bus.getName())) {
						result = bus;
						break;
					}
				}
			}
		}

		return result;
	}

	/**
	 * getBusCount
	 * 
	 * @return int
	 */
	public int getBusCount() {
		int result = 0;

		for (Board board : getInheritanceList()) {
			result += (board.buses != null) ? board.buses.size() : 0;
		}

		return result;
	}

	/**
	 * getBuses
	 * 
	 * @return List<Bus>
	 */
	public List<Bus> getBuses() {
		List<Bus> result = new ArrayList<Bus>();

		for (Board board : getInheritanceList()) {
			if (board.buses != null) {
				result.addAll(board.buses);
			}
		}

		return result;
	}

	/**
	 * getBusIndex
	 * 
	 * @param bus
	 * @return int
	 */
	public int getBusIndex(Bus bus) {
		int offset = 0;
		int result = -1;

		for (Board board : getInheritanceList()) {
			if (board.buses != null) {
				int index = board.buses.indexOf(bus);

				if (index != 1) {
					result = offset + index;
					break;
				} else {
					offset += board.getBusCount();
				}
			}
		}

		return result;
	}

	/**
	 * getBusNames
	 * 
	 * @return Set<String>
	 */
	public Set<String> getBusNames() {
		Set<String> result = new CaselessSet(new LinkedHashSet<CaselessString>());

		for (Board board : getInheritanceList()) {
			if (board.buses != null) {
				for (Bus bus : board.buses) {
					result.add(bus.getName());
				}
			}
		}

		return result;
	}

	/**
	 * getConfigs
	 * 
	 * @return
	 */
	public List<Tuple<String, String>> getConfigs() {
		List<Tuple<String, String>> result = new ArrayList<Tuple<String, String>>();

		for (Board board : getInheritanceList()) {
			if (board.configs != null) {
				result.addAll(board.configs);
			}
		}

		return result;
	}

	/**
	 * getConfigsString
	 * 
	 * @param configuration
	 * @return
	 */
	public String getConfigsString(Set<Configuration> configuration) {
		StringBuilder buffer = new StringBuilder();
		List<Tuple<String, String>> configs = getConfigs();

		if (!configs.isEmpty()) {
			for (Tuple<String, String> config : configs) {
				buffer.append("CONFIG ").append(config.first);

				if (config.last != null) {
					if (configuration.contains(Configuration.SPACE_AROUND_EQUALS)) {
						buffer.append(" = ");
					} else {
						buffer.append("=");
					}

					buffer.append(config.last).append(';').append(StringUtils.EOL);
				}
			}

			buffer.append(StringUtils.EOL);
		}

		return buffer.toString();
	}

	/**
	 * getHeader
	 * 
	 * @return String
	 */
	public String getHeader() {
		StringBuilder buffer = new StringBuilder();

		for (Board board : getInheritanceList()) {
			if (board.header != null) {
				buffer.append(board.header).append(StringUtils.EOL);
			}
		}

		return buffer.toString();
	}

	/**
	 * getInheritanceList
	 * 
	 * @return
	 */
	protected List<Board> getInheritanceList() {
		List<Board> result = new ArrayList<Board>();
		Board currentBoard = this;

		while (currentBoard != null) {
			result.add(currentBoard);

			currentBoard = currentBoard.getParentBoard();
		}

		Collections.reverse(result);

		return result;
	}

	/**
	 * getNames
	 * 
	 * @return List<String>
	 */
	public List<String> getNames() {
		return names;
	}

	/**
	 * getParentBoard
	 * 
	 * @return String
	 */
	public Board getParentBoard() {
		Board result = null;

		if (Registrar.BOARD_REGISTRY.contains(parentBoard)) {
			result = Registrar.BOARD_REGISTRY.getBoard(parentBoard);
		}

		return result;
	}

	/**
	 * getPin
	 * 
	 * @param pinName
	 * @return Pin
	 */
	public Pin getPin(String pinName) {
		Pin result = null;

		if (pinName != null) {
			for (Pin pin : getPins()) {
				if (pinName.equals(pin.getName())) {
					result = pin;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * getPinList
	 * 
	 * @return List<Pin>
	 */
	public List<Pin> getPins() {
		List<Pin> result = new ArrayList<Pin>();

		for (Board board : getInheritanceList()) {
			if (board.buses != null) {
				for (Bus bus : board.buses) {
					for (Pin pin : bus.getPins()) {
						result.add(pin);
					}
				}
			}
		}

		return result;
	}

	/**
	 * isWing
	 * 
	 * @return boolean
	 */
	public boolean isWing() {
		return isWing;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Bus> iterator() {
		Stack<Bus> allBuses = new Stack<Bus>();

		for (Board board : getInheritanceList()) {
			if (board.buses != null) {
				allBuses.addAll(board.buses);
			}
		}

		return allBuses.iterator();
	}

	/**
	 * placeWing
	 * 
	 * @param wing
	 * @param busName
	 */
	public void placeWing(Board wing, String busName) {
		String name;
		int startingIndex;

		Pattern pattern = Pattern.compile("([^0-9\\r\\n\\s]+)([0-9]+)");
		Matcher matcher = pattern.matcher(busName);

		if (matcher.matches()) {
			name = matcher.group(1);
			startingIndex = Integer.parseInt(matcher.group(2));
		} else {
			name = busName;
			startingIndex = 0;
		}

		int busIndex = getBusIndex(getBus(name));

		if (busIndex != -1) {
			for (Bus wingBus : wing) {
				Bus bus = getBus(busIndex);

				if (0 <= startingIndex && startingIndex + wingBus.getPinCount() <= bus.getPinCount()) {
					for (int i = 0; i < wingBus.getPinCount(); i++) {
						Pin pin = bus.getPin(startingIndex + i);
						Pin wingPin = new Pin(wingBus.getPin(i));

						wingPin.setNextPin(pin);
						bus.setPin(startingIndex + i, wingPin);
					}
				}

				// reset startingIndex for any remaining buses on the wing
				startingIndex = 0;
				busIndex++;
			}
		}
	}

	/**
	 * setConfigs
	 * 
	 * @param configs
	 */
	public void setConfigs(List<Tuple<String, String>> configs) {
		this.configs = configs;
	}

	/**
	 * setHeader
	 * 
	 * @param header
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * setIsWing
	 * 
	 * @param flag
	 */
	public void setIsWing(boolean flag) {
		isWing = flag;
	}

	/**
	 * setParentBoardName
	 * 
	 * @param name
	 */
	public void setParentBoardName(String name) {
		parentBoard = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();

		if (names != null && names.size() > 0) {
			buffer.append('"').append(StringUtils.join("\",\"", names)).append('"');
		}
		buffer.append(" {").append(StringUtils.EOL);

		if (buses != null && buses.size() > 0) {
			List<String> busStrings = new ArrayList<String>();

			for (Bus bus : buses) {
				busStrings.add("  " + bus.toString());
			}

			buffer.append(StringUtils.join(StringUtils.EOL, busStrings)).append(StringUtils.EOL);
		}

		buffer.append('}');

		return buffer.toString();
	}

	/**
	 * toUCF
	 * 
	 * @param nameFilter
	 * @return String
	 */
	public String toUCF(Set<String> nameFilter) {
		Table table = new Table();

		toUCF(table, nameFilter, EnumSet.noneOf(Configuration.class));

		return table.toString();
	}

	/**
	 * // $codepro.audit.disable blockDepth toUCF
	 * 
	 * @param table
	 * @param nameFilter
	 * @param configuration
	 */
	public void toUCF(Table table, Set<String> nameFilter, Set<Configuration> configuration) {
		if (getBusCount() > 0) {
			if (nameFilter != null) {
				// TODO: fix cartesian product. We do this to preserve output
				// order
				for (String name : nameFilter) {
					CaselessString caselessName = new CaselessString(name);

					for (Bus bus : getBuses()) {
						if (caselessName.equals(bus.getName())) {
							bus.toUCF(table, nameFilter, configuration);
							break;
						}
					}
				}
			} else {
				for (Bus bus : getBuses()) {
					bus.toUCF(table, nameFilter, configuration);
				}
			}
		}
	}
}
