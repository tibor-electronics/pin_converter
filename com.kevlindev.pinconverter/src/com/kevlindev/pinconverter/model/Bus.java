/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kevlindev.collections.CaselessString;
import com.kevlindev.text.Table;
import com.kevlindev.utils.StringUtils;

/**
 * Bus
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class Bus {
	/**
	 * The board to which this bus belongs
	 */
	private Board owningBoard;

	/**
	 * The name of this bus
	 */
	private String name;

	/**
	 * An alias to use when outputting this bus to a UCF file
	 */
	private String alias;

	/**
	 * A list of pins in this bus
	 */
	private List<Pin> pins;

	/**
	 * A map of key/value pair additions to be applied to all pins in this bus.
	 * Note that a pair containing a null value will emit the key name only
	 */
	private Map<String, String> additions;

	/**
	 * Bus
	 * 
	 * @param name
	 */
	public Bus(String name) {
		this.name = name;
	}

	/**
	 * Create a copy of a bus from another bus.
	 * 
	 * @param bus
	 */
	public Bus(Bus bus) {
		owningBoard = bus.owningBoard;
		name = bus.name;
		alias = bus.alias;

		if (bus.pins != null) {
			pins = new ArrayList<Pin>();

			for (Pin pin : bus.pins) {
				Pin newPin = new Pin(pin);

				pins.add(newPin);
			}
		}
	}

	/**
	 * getAdditions
	 * 
	 * @return List<String>
	 */
	public Map<String, String> getAdditions() {
		return additions;
	}

	/**
	 * getAlias
	 * 
	 * @return String
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * getName
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * getOwningBoard
	 * 
	 * @return Board
	 */
	public Board getOwningBoard() {
		return owningBoard;
	}

	/**
	 * getPin
	 * 
	 * @param index
	 * @return Pin
	 */
	public Pin getPin(int index) {
		Pin result = null;

		if (pins != null && 0 <= index && index < pins.size()) {
			result = pins.get(index);
		}

		return result;
	}

	/**
	 * getPinCount
	 * 
	 * @return int
	 */
	public int getPinCount() {
		return (pins != null) ? pins.size() : 0;
	}

	/**
	 * getPinIndex
	 * 
	 * @param pin
	 * @return int
	 */
	public int getPinIndex(Pin pin) {
		int result = -1;

		if (pins != null) {
			result = pins.indexOf(pin);
		}

		return result;
	}

	/**
	 * getPins
	 * 
	 * @return List<Pin>
	 */
	public List<Pin> getPins() {
		return pins;
	}

	/**
	 * getUCFName
	 * 
	 * @return String
	 */
	public String getUCFName() {
		return (alias != null && alias.length() > 0) ? alias : name;
	}

	/**
	 * getUCFName
	 * 
	 * @param pin
	 * @return String
	 */
	public String getUCFName(Pin pin) {
		return getUCFName(pin, true);
	}

	/**
	 * getUCFName
	 * 
	 * @param pin
	 * @param asBus
	 * @return String
	 */
	public String getUCFName(Pin pin, boolean asBus) {
		String result = StringUtils.EMPTY;

		if (pin != null) {
			String pinAlias = pin.getAlias();
			String netName = (pinAlias != null && pinAlias.length() > 0) ? pinAlias : getUCFName();

			if (getPinCount() > 1) {
				if (asBus) {
					result = MessageFormat.format("{0}({1})", netName, pin.getIndex());
				} else {
					result = netName + pin.getIndex();
				}
			} else {
				result = netName;
			}
		}

		return result;
	}

	/**
	 * hasAdditions
	 * 
	 * @return boolean
	 */
	public boolean hasAdditions() {
		return additions != null && additions.size() > 0;
	}

	/**
	 * setAdditions
	 * 
	 * @param additions
	 */
	public void setAdditions(Map<String, String> additions) {
		this.additions = additions;
	}

	/**
	 * setAlias
	 * 
	 * @param alias
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * setOwningBoard
	 * 
	 * @param board
	 */
	public void setOwningBoard(Board board) {
		owningBoard = board;
	}

	/**
	 * setPin
	 * 
	 * @param index
	 * @param pin
	 * @return Pin
	 */
	public Pin setPin(int index, Pin pin) {
		Pin result = null;

		if (pins != null && 0 <= index && index < pins.size()) {
			result = pins.set(index, pin);
		}

		return result;
	}

	/**
	 * setPins
	 * 
	 * @param pins
	 */
	public void setPins(List<Pin> pins) {
		this.pins = pins;

		if (pins != null) {
			for (Pin pin : pins) {
				pin.setOwningBus(this);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();

		buffer.append('"').append(name).append("\":");

		// emit pins
		if (pins != null && pins.size() > 0) {
			List<String> pinStrings = new ArrayList<String>();

			for (Pin pin : pins) {
				pinStrings.add(pin.toString());
			}

			buffer.append(StringUtils.join(",", pinStrings));
		}

		return buffer.toString();
	}

	/**
	 * toUCF
	 * 
	 * @param table
	 * @param nameFilter
	 * @param configuration
	 */
	public void toUCF(Table table, Set<String> nameFilter, Set<Configuration> configuration) {
		if (pins != null) {
			for (Pin pin : pins) {
				CaselessString canonicalName = new CaselessString(pin.getCanonicalName());

				if (nameFilter == null || nameFilter.contains(canonicalName)) {
					pin.toUCF(table, configuration);
				}
			}
		}
	}
}
