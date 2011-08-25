/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.switches;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kevlindev.pinconverter.ExecutionContext;
import com.kevlindev.pinconverter.PinConverter;
import com.kevlindev.pinconverter.commands.ICommand;
import com.kevlindev.text.Table;
import com.kevlindev.text.TableRow;
import com.kevlindev.utils.StringUtils;

/**
 * HelpSwitch
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class HelpSwitch extends AbstractSwitch {
	private static final String[] PREFIXES = new String[] { StringUtils.EMPTY, "-", "--" };

	/**
	 * The main class that holds a list of all switches.
	 */
	private PinConverter pinConverter;

	/**
	 * The name of the switch for which to provide help info
	 */
	private String switchName;

	/**
	 * HelpSwitch
	 * 
	 * @param pinConverter
	 */
	public HelpSwitch(PinConverter pinConverter) {
		this.pinConverter = pinConverter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.AbstractSwitch#createCommand()
	 */
	@Override
	public ICommand createCommand() {
		return new ICommand() {
			protected TableRow createRow(String name, String value) {
				TableRow row = new TableRow();

				row.addColumns(name, value);

				return row;
			}

			@Override
			public boolean execute(ExecutionContext context) {
				boolean result = false;

				if (pinConverter != null) {
					ISwitch sw = null;

					for (String prefix : PREFIXES) {
						String name = prefix + switchName;

						sw = pinConverter.getSwitch(name);

						if (sw != null) {
							// create table
							Table table = new Table();

							// right-align the row header column
							table.setAlignment(0, Table.Alignment.RIGHT);

							// add rows
							table.addRow(createRow("Name:", sw.getDisplayName()));
							table.addRow(createRow("Switches:", StringUtils.join(", ", sw.getSwitchNames())));
							table.addRow(createRow("Description:", sw.getLongDescription()));

							// resize table
							table.setWidth(80, 20);

							// show help
							System.out.println(table.toString());
							result = true;
							break;
						}
					}

					if (sw == null) {
						System.err.println("Unrecognized switch '" + switchName + "'");
					}
				}

				return result;
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.AbstractSwitch#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Provide help on a specified switch.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.ISwitch#getDisplayName()
	 */
	@Override
	public String getDisplayName() {
		return "Help";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.pinconverter.switches.AbstractSwitch#getAliases()
	 */
	@Override
	public List<String> getSwitchNames() {
		List<String> aliases = new ArrayList<String>();

		aliases.add("-h");
		aliases.add("--help");

		return aliases;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.kevlindev.pinconverter.switches.AbstractSwitch#processArg(java.util
	 * .Iterator)
	 */
	@Override
	public boolean processArg(Iterator<String> args) {
		boolean result = false;

		if (args.hasNext()) {
			switchName = args.next();

			result = true;
		}

		return result;
	}
}
