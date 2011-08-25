/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter;

import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.kevlindev.collections.CaselessMap;
import com.kevlindev.pinconverter.model.Board;
import com.kevlindev.pinconverter.model.Configuration;
import com.kevlindev.pinconverter.model.Pin;

/**
 * ExecutionContext
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class ExecutionContext {
	/**
	 * The name of the board to use as the source when executing some commands
	 */
	private String sourceBoardName;

	/**
	 * The name of the board to use as the destination when executing some
	 * commands
	 */
	private String destinationBoardName;

	/**
	 * The full or relative path of the file to use as input
	 */
	private String inputFileName;

	/**
	 * The full or relative path of the file to use as output
	 */
	private String outputFileName;

	/**
	 * A caseless map used for moving pins from one location to another
	 */
	private Map<String, String> translationMap;

	/**
	 * A map of containing a wing name and the pin where it should be placed
	 */
	private Map<String, String> wingPlacements;

	/**
	 * A set of flags
	 */
	private EnumSet<Configuration> configuration = EnumSet.noneOf(Configuration.class);

	/**
	 * Add a wing and its root header pin name to a list of wing placements.
	 * These are used during UCF generation to override the header pin names to
	 * use the wing names in their place
	 * 
	 * @param destinationPin
	 *            The root location for the first pin of the wing.
	 * @param wingName
	 *            The name of the wing board to apply during generation
	 */
	public void addWingPlacement(String destinationPin, String wingName) {
		if (wingPlacements == null) {
			wingPlacements = new CaselessMap<String>();
		}

		if (wingPlacements.containsKey(destinationPin)) {
			String message = MessageFormat.format("Wing {0} will override wing {1} at header pin {2}", wingName, wingPlacements.get(destinationPin),
					destinationPin);
			System.err.println(message);
		}

		wingPlacements.put(destinationPin, wingName);
	}

	/**
	 * collapseColumns
	 * 
	 * @return boolean
	 */
	public boolean collapseColumns() {
		return configuration.contains(Configuration.COLLAPSE_COLUMNS);
	}

	/**
	 * excludeUnusedPins
	 * 
	 * @return boolean
	 */
	public boolean excludeUnusedPins() {
		return configuration.contains(Configuration.EXCLUDE_UNUSED_PINS);
	}

	/**
	 * Get the map of header pin names to FPGA pin names for the specified
	 * board. If the board does not exist, then return null
	 * 
	 * @param boardName
	 *            The name of the board
	 * @return A map of header pin names to FGPA pin names.
	 */
	private Board getBoardPinMap(String boardName) {
		return Registrar.BOARD_REGISTRY.getBoard(boardName);
	}

	/**
	 * getConfiguration
	 * 
	 * @return Set<Configuration>
	 */
	public Set<Configuration> getConfiguration() {
		return configuration;
	}

	/**
	 * Return a map of header pin names to FPGA pin names for the destination
	 * board. If the destination board is not defined, but the source board is,
	 * then this returns the source boards pin map. If both source and
	 * destination boards are null, then an empty map is returned
	 * 
	 * @return A map of header pin names to their associated FPGA pin names
	 */
	public Board getDestinationBoard() {
		return getBoardPinMap(getDestinationBoardName());
	}

	/**
	 * Return the destination board type. If this value is null, then the source
	 * board type is returned, which may be null
	 * 
	 * @return The string name of the destination board. This may be null
	 */
	public String getDestinationBoardName() {
		return (destinationBoardName != null) ? destinationBoardName : sourceBoardName;
	}

	/**
	 * Get the name of the file to be used for input.
	 * 
	 * @return A string name of the file to be used for input. This may be null
	 */
	public String getInputFileName() {
		return inputFileName;
	}

	/**
	 * Get the name of the file to be used for output.
	 * 
	 * @return A string name of the file to be used for output. This may be null
	 */
	public String getOutputFileName() {
		return outputFileName;
	}

	/**
	 * getPinMap
	 * 
	 * @return Map<String,String>
	 */
	public Map<String, String> getPinMap() {
		Map<String, String> pinMap = new HashMap<String, String>();

		// load up source pin map
		Board sourceBoard = getSourceBoard();
		Board destinationBoard = getDestinationBoard();

		// build map of destination pin UCF names to pins
		Map<String, Pin> destinationPinsByUCFName = new CaselessMap<Pin>();

		for (Pin destinationPin : destinationBoard.getPins()) {
			destinationPinsByUCFName.put(destinationPin.getUCFName(false), destinationPin);
		}

		// build final map taking any tranlations into account
		for (Pin sourcePin : sourceBoard.getPins()) {
			String source = sourcePin.getUCFName(false);
			String destination = (translationMap != null && translationMap.containsKey(source)) ? translationMap.get(source) : source;

			Pin destinationPin = destinationPinsByUCFName.get(destination);

			if (destinationPin != null) {
				pinMap.put(sourcePin.getName(), destinationPin.getName());
			}
		}

		return pinMap;
	}

	// TODO: document and possibly rename

	/**
	 * Return a map of header pin names to FPGA pin names for the source board.
	 * If the source board is not defined, but the destination board is, then
	 * this returns the destination boards pin map. If both source and
	 * destination boards are null, then an empty map is returned
	 * 
	 * @return A map of header pin names to their associated FPGA pin names
	 */
	public Board getSourceBoard() {
		return getBoardPinMap(getSourceBoardName());
	}

	/**
	 * Return the source board type. If this value is null, then the destination
	 * board type is returned, which may be null
	 * 
	 * @return The string name of the source board. This may be null
	 */
	public String getSourceBoardName() {
		return (sourceBoardName != null) ? sourceBoardName : destinationBoardName;
	}

	/**
	 * getSpacesAroundEquals
	 * 
	 * @return boolean
	 */
	public boolean getSpacesAroundEquals() {
		return configuration.contains(Configuration.SPACE_AROUND_EQUALS);
	}

	/**
	 * Return a map of header pin names to wing names. This is used during UCF
	 * generation to place wings within wing slots
	 * 
	 * @return A map of header ping names to wing names. This may be null
	 */
	public Map<String, String> getWingPlacementMap() {
		return wingPlacements;
	}

	/**
	 * isSorted
	 * 
	 * @return boolean
	 */
	public boolean isSorted() {
		return configuration.contains(Configuration.SORTED);
	}

	/**
	 * Send the specified output to STDOUT or to a file. If an output filename
	 * is defined, output goes to that file; otherwise, the output goes to
	 * STDOUT
	 * 
	 * @param output
	 *            The text to output
	 */
	public void outputResult(String output) {
		if (outputFileName != null && !outputFileName.isEmpty()) {
			FileWriter writer = null;

			try {
				writer = new FileWriter(outputFileName);
				writer.write(output);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) { // $codepro.audit.disable
												// emptyCatchClause
					}
				}
			}
		} else {
			System.out.print(output);
		}
	}

	/**
	 * setColumnsCollapsed
	 * 
	 * @param collapseColumns
	 */
	public void setColumnsCollapsed(boolean collapseColumns) {
		if (collapseColumns) {
			configuration.add(Configuration.COLLAPSE_COLUMNS);
		} else {
			configuration.remove(Configuration.COLLAPSE_COLUMNS);
		}
	}

	/**
	 * Set the destination board type
	 * 
	 * @param type
	 *            The board type. This may be null
	 */
	public void setDestinationBoardName(String type) {
		destinationBoardName = type;
	}

	/**
	 * setExcludeUnusedPins
	 * 
	 * @param excludeUnusedPins
	 */
	public void setExcludeUnusedPins(boolean excludeUnusedPins) {
		if (excludeUnusedPins) {
			configuration.add(Configuration.EXCLUDE_UNUSED_PINS);
		} else {
			configuration.remove(Configuration.EXCLUDE_UNUSED_PINS);
		}
	}

	/**
	 * Set the input file name
	 * 
	 * @param inputFileName
	 *            The input file name. This may be null, although commands
	 *            expecting an input file (--verify, --transform) may fail as a
	 *            result.
	 */
	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	/**
	 * setIsSorted
	 * 
	 * @param sorted
	 */
	public void setIsSorted(boolean sorted) {
		if (sorted) {
			configuration.add(Configuration.SORTED);
		} else {
			configuration.remove(Configuration.SORTED);
		}
	}

	/**
	 * Set the output file name
	 * 
	 * @param outputFileName
	 *            The output file name. This may be null
	 */
	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	/**
	 * Set the translation map to be used when retrieving the transformation
	 * map. This moves source board pin names to point to different wings on the
	 * destination board
	 * 
	 * @param translationMap
	 *            A map of source header pin names to destination header pin
	 *            names. This may be null
	 */
	public void setPinTranslationMap(Map<String, String> translationMap) {
		this.translationMap = translationMap;
	}

	/**
	 * Set the source board type
	 * 
	 * @param type
	 *            The board type. This may be null
	 */
	public void setSourceBoardName(String type) {
		sourceBoardName = type;
	}

	/**
	 * setSpacesAroundEquals
	 * 
	 * @param useSpaces
	 */
	public void setSpacesAroundEquals(boolean useSpaces) {
		if (useSpaces) {
			configuration.add(Configuration.SPACE_AROUND_EQUALS);
		} else {
			configuration.remove(Configuration.SPACE_AROUND_EQUALS);
		}
	}
}
