/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kevlindev.text.Table;
import com.kevlindev.utils.ObjectUtils;
import com.kevlindev.utils.StringUtils;

/**
 * Pin
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class Pin {
	/**
	 * The bus to which this pin belongs
	 */
	private Bus owningBus;

	/**
	 * The FPGA pin name that this represents
	 */
	private String name;

	/**
	 * Any additional UCF configuration
	 */
	private Map<String, String> additions;

	/**
	 * An alias to use when outputting this bus to a UCF file. Note that if this
	 * name is defined and the owning Bus also defines an alias, this alias wins
	 */
	private String alias;

	/**
	 * A pin below this pin. This allows us to stack boards on top of one
	 * another
	 */
	private Pin nextPin;

	/**
	 * Pin
	 */
	public Pin() {
	}

	/**
	 * Pin
	 * 
	 * @param pin
	 */
	public Pin(Pin pin) {
		owningBus = pin.owningBus;
		name = pin.name;

		if (pin.additions != null) {
			additions = new LinkedHashMap<String, String>(pin.additions);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof Pin) {
			Pin other = (Pin) obj;
			boolean additionsTest = (additions != null) ? additions.equals(other.additions) : other.additions == null;

			return ObjectUtils.equal(name, other.name) && additionsTest && ObjectUtils.equal(alias, other.alias) && ObjectUtils.equal(nextPin, other.nextPin);
		} else {
			return super.equals(obj);
		}
	}

	/**
	 * getAdditions
	 * 
	 * @return List<String>
	 */
	public Map<String, String> getAdditions() {
		Bus owningBus = getOwningBus();
		Map<String, String> result = new LinkedHashMap<String, String>();

		if (owningBus != null) {
			Map<String, String> additions = owningBus.getAdditions();

			if (additions != null) {
				result.putAll(additions);
			}
		}

		if (additions != null) {
			result.putAll(additions);
		}

		return result;
	}

	/**
	 * getAdditionsString
	 * 
	 * @param delimiter
	 * @return
	 */
	protected String getAdditionsString(String delimiter) {
		return getAdditionsString(delimiter, EnumSet.noneOf(Configuration.class));
	}

	/**
	 * getAdditionsString
	 * 
	 * @param delimiter
	 * @param configuration
	 * @return String
	 */
	protected String getAdditionsString(String delimiter, Set<Configuration> configuration) {
		// build combined list of entries from owning bus and this pin
		Map<String, String> entries = new LinkedHashMap<String, String>();
		Bus owningBus = getOwningBus();

		if (owningBus.hasAdditions()) {
			entries.putAll(owningBus.getAdditions());
		}

		if (additions != null) {
			entries.putAll(additions);
		}

		// build a list of assignments to join, taking configuration into
		// account
		List<String> items = new ArrayList<String>();

		for (Map.Entry<String, String> entry : entries.entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue();

			if (value != null) {
				if (configuration.contains(Configuration.SPACE_AROUND_EQUALS)) {
					items.add(name + " = " + value);
				} else {
					items.add(name + '=' + value);
				}
			} else {
				items.add(name);
			}
		}

		// return joined result
		return StringUtils.join(delimiter, items);
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
	 * getBoardPin
	 * 
	 * @return Pin
	 */
	private Pin getBoardPin() {
		Pin result = this;

		while (result.nextPin != null) {
			result = result.nextPin;
		}

		return result;
	}

	/**
	 * getCanonicalName
	 * 
	 * @return String
	 */
	public String getCanonicalName() {
		Pin boardPin = getBoardPin();
		Bus boardBus = boardPin.getOwningBus();
		String result = StringUtils.EMPTY;

		if (boardBus.getPinCount() > 1) {
			result = boardBus.getName() + boardBus.getPinIndex(this);
		} else {
			result = boardBus.getName();
		}

		return result;
	}

	/**
	 * getIndex
	 * 
	 * @return int
	 */
	public int getIndex() {
		return (owningBus != null) ? owningBus.getPinIndex(this) : -1;
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
	 * getNextPin
	 * 
	 * @return Pin
	 */
	public Pin getNextPin() {
		return nextPin;
	}

	/**
	 * getOwningBus
	 * 
	 * @return Bus
	 */
	public Bus getOwningBus() {
		return owningBus;
	}

	/**
	 * getUCFName
	 * 
	 * @return String
	 */
	public String getUCFName() {
		return getUCFName(true);
	}

	/**
	 * getUCFName
	 * 
	 * @param asBus
	 * @return String
	 */
	public String getUCFName(boolean asBus) {
		return owningBus.getUCFName(this, asBus);
	}

	/**
	 * hasAdditions
	 * 
	 * @return boolean
	 */
	public boolean hasAdditions() {
		Bus owningBus = getOwningBus();

		return (additions != null && additions.size() > 0) || (owningBus != null && owningBus.hasAdditions());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int nameHash = (name != null) ? name.hashCode() : 0;
		int additionsHash = (additions != null) ? additions.hashCode() : 0;
		int aliasHash = (alias != null) ? alias.hashCode() : 0;

		int hash = nameHash;
		hash = 31 * hash + additionsHash;
		hash = 31 * hash * aliasHash;

		return hash;
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
	 * setName
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * setNextPin
	 * 
	 * @param nextPin
	 */
	public void setNextPin(Pin nextPin) {
		this.nextPin = nextPin;
	}

	/**
	 * setOwningBus
	 * 
	 * @param owningBus
	 */
	public void setOwningBus(Bus owningBus) {
		this.owningBus = owningBus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();

		buffer.append(name);

		if (hasAdditions()) {
			buffer.append('{');
			buffer.append('"').append(getAdditionsString("\", \"")).append('"');
			buffer.append('}');
		}

		return buffer.toString();
	}

	/**
	 * toUCF
	 * 
	 * @param table
	 * @param configuration
	 */
	public void toUCF(Table table, Set<Configuration> configuration) {
		String column1 = null;
		String column2;
		String column3 = StringUtils.EMPTY;
		String column4;

		Pin boardPin = getBoardPin();
		Bus boardBus = boardPin.getOwningBus();
		String headerName = (owningBus.getOwningBoard().isWing()) ? getName() : boardBus.getUCFName(this);
		String name = (owningBus.getOwningBoard().isWing()) ? boardPin.getName() : getName();

		if (headerName != null && headerName.length() != 0) {
			column1 = String.format("NET %s", headerName);
		} else {
			if (!configuration.contains(Configuration.EXCLUDE_UNUSED_PINS)) {
				column1 = String.format("#NET %s", getCanonicalName());
			}
		}

		// a null column1 indicates that we should not emit this pin
		if (column1 != null) {
			if (configuration.contains(Configuration.SPACE_AROUND_EQUALS)) {
				column2 = String.format("LOC = \"%s\"", name);
			} else {
				column2 = String.format("LOC=\"%s\"", name);
			}

			if (hasAdditions()) {
				column3 = "| " + getAdditionsString(" | ", configuration) + ";";
			}

			column4 = "# " + getCanonicalName();

			table.addRow(column1, column2, column3, column4);
		}
	}
}
