/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.kevlindev.logging.Logger;
import com.kevlindev.pinconverter.commands.AbstractLoadCommand;
import com.kevlindev.pinconverter.commands.ICommand;
import com.kevlindev.pinconverter.commands.InputFileCommand;
import com.kevlindev.pinconverter.commands.TransformCommand;
import com.kevlindev.pinconverter.switches.CollapseColumnsSwitch;
import com.kevlindev.pinconverter.switches.DestinationBoardSwitch;
import com.kevlindev.pinconverter.switches.ExcludeUnusedPinsSwitch;
import com.kevlindev.pinconverter.switches.GenerateSwitch;
import com.kevlindev.pinconverter.switches.HelpSwitch;
import com.kevlindev.pinconverter.switches.ISwitch;
import com.kevlindev.pinconverter.switches.InputFileSwitch;
import com.kevlindev.pinconverter.switches.ListBoardsSwitch;
import com.kevlindev.pinconverter.switches.LoadBoardSwitch;
import com.kevlindev.pinconverter.switches.LoadWingSwitch;
import com.kevlindev.pinconverter.switches.MoveSwitch;
import com.kevlindev.pinconverter.switches.OutputFileSwitch;
import com.kevlindev.pinconverter.switches.PlaceWingSwitch;
import com.kevlindev.pinconverter.switches.SortSwitch;
import com.kevlindev.pinconverter.switches.SourceBoardSwitch;
import com.kevlindev.pinconverter.switches.SpacesAroundEqualsSwitch;
import com.kevlindev.pinconverter.switches.TransformSwitch;
import com.kevlindev.pinconverter.switches.ValidateSwitch;
import com.kevlindev.pinconverter.switches.VersionSwitch;
import com.kevlindev.text.Table;
import com.kevlindev.utils.StringUtils;

/**
 * PinConverter
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class PinConverter {
	/**
	 * A pattern used to match net/loc definitions in UCF files
	 */
	public static final Pattern NET_PATTERN = Pattern.compile("^(\\s*NET\\s*\\S+\\s*LOC\\s*=\\s*\")([^\"]+)(\".*$)");

	/**
	 * The name of the default board definition resource
	 */
	private static final String DEFAULT_BOARD_RESOURCE = "/com/kevlindev/pinconverter/resources/boards.txt";

	/**
	 * The name of the default wing definition resource
	 */
	private static final String DEFAULT_WING_RESOURCE = "/com/kevlindev/pinconverter/resources/wings.txt";

	/**
	 * main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		PinConverter converter = new PinConverter();

		converter.processArgs(args);
		converter.run();
	}

	/**
	 * A map of switches by their names and aliases
	 */
	private Map<String, ISwitch> switchMap;

	/**
	 * A list of commands to execute after all switches have been processed
	 */
	private List<ICommand> commands = new ArrayList<ICommand>();

	/**
	 * PinConverter
	 */
	public PinConverter() {
		Logger.getInstance().addLogListener(new ConsoleLogger());
	}

	/**
	 * Add a new switch to the list of switches associated with this tool. Note
	 * that switches are stored internally in a map. The map associates the
	 * switch names (main name and aliases) with the switch itself. If any
	 * previously added switch used any of those names, that switch will be
	 * replaced with the new switch where the names overlap
	 * 
	 * @param swtch
	 *            The new switch to add to this tool. Null values are ignored
	 */
	protected void addSwitch(ISwitch swtch) {
		if (swtch != null) {
			if (switchMap == null) {
				switchMap = new HashMap<String, ISwitch>();
			}

			for (String alias : swtch.getSwitchNames()) {
				switchMap.put(alias, swtch);
			}
		}
	}

	/**
	 * getSwitch
	 * 
	 * @param name
	 * @return ISwitch
	 */
	public ISwitch getSwitch(String name) {
		return (switchMap != null) ? switchMap.get(name) : null;
	}

	/**
	 * Build a list of switches supported by this tool. It would be nice to be
	 * able to generate this list via reflection, like you can in .NET
	 */
	protected void initializeSwitches() {
		if (switchMap == null) {
			addSwitch(new CollapseColumnsSwitch());
			addSwitch(new DestinationBoardSwitch());
			addSwitch(new ExcludeUnusedPinsSwitch());
			addSwitch(new GenerateSwitch());
			addSwitch(new HelpSwitch(this));
			addSwitch(new InputFileSwitch());
			addSwitch(new ListBoardsSwitch());
			addSwitch(new LoadBoardSwitch());
			addSwitch(new LoadWingSwitch());
			addSwitch(new MoveSwitch());
			addSwitch(new OutputFileSwitch());
			addSwitch(new PlaceWingSwitch());
			addSwitch(new SortSwitch());
			addSwitch(new SourceBoardSwitch());
			addSwitch(new SpacesAroundEqualsSwitch());
			addSwitch(new TransformSwitch());
			addSwitch(new ValidateSwitch());
			addSwitch(new VersionSwitch());
		}
	}

	/**
	 * Process all of the command-line arguments in the order in which they
	 * appear on the command-line.
	 * 
	 * @param args
	 *            The list of arguments to process
	 */
	private void processArgs(String[] args) {
		initializeSwitches();

		if (args.length > 0) {

			// TODO: pull out common class
			// add command to load default board registries
			commands.add(new AbstractLoadCommand(null) {
				@Override
				protected InputStream getInputStream() {
					return PinConverter.class.getResourceAsStream(DEFAULT_BOARD_RESOURCE);
				}

				@Override
				protected BoardRegistry getRegistry() {
					return Registrar.BOARD_REGISTRY;
				}
			});
			commands.add(new AbstractLoadCommand(null) {
				@Override
				protected InputStream getInputStream() {
					return PinConverter.class.getResourceAsStream(DEFAULT_WING_RESOURCE);
				}

				@Override
				protected BoardRegistry getRegistry() {
					return Registrar.WING_REGISTRY;
				}
			});

			// walk the args and process each
			Iterator<String> argIterator = Arrays.asList(args).iterator();

			while (argIterator.hasNext()) {
				String switchName = argIterator.next();

				if (switchMap.containsKey(switchName)) {
					ISwitch currentSwitch = switchMap.get(switchName);

					currentSwitch.processArg(argIterator);
					commands.add(currentSwitch.createCommand());
				} else if (argIterator.hasNext()) {
					Logger.logError("Unrecognized switch: " + switchName);
					usage();
					break;
				} else {
					commands.add(new InputFileCommand(switchName));
					commands.add(new TransformCommand());
				}
			}
		} else {
			usage();
		}
	}

	/**
	 * Execute all commands generated while processing the command-line
	 * arguments.
	 */
	protected void run() {
		ExecutionContext context = new ExecutionContext();

		for (ICommand command : commands) {
			if (!command.execute(context)) {
				Logger.logError("execution failed: " + command.getClass().getName());
				break;
			}
		}
	}

	/**
	 * Print out usage information about all of the switches supported by this
	 * tool
	 */
	protected void usage() {
		System.out.println("usage: pinconv [options]+ [input-file]");
		System.out.println();

		// get all switches, including aliases, and sort them
		List<String> switches = new ArrayList<String>(switchMap.keySet());
		Collections.sort(switches);

		Table table = new Table();

		table.addHeaders("Option", "Name", "Aliases", "Description");

		// print out a description for each non-alias switch
		for (String switchName : switches) {
			ISwitch currentSwitch = switchMap.get(switchName);
			List<String> names = currentSwitch.getSwitchNames();
			String name = names.remove(0);

			// skip aliases
			if (name.equals(switchName)) {
				String displayName = currentSwitch.getDisplayName();
				String aliases = (names.isEmpty()) ? "" : StringUtils.join("\n", names);
				String description = currentSwitch.getDescription();

				table.addRow(name, displayName, aliases, description);
			}
		}

		// resize table
		table.setWidth(80, 20);

		System.out.println(table);
	}
}
