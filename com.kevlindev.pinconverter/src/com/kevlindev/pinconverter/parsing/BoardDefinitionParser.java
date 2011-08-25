/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.parsing;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kevlindev.collections.Tuple;
import com.kevlindev.parsing.ILexer;
import com.kevlindev.parsing.Lexeme;
import com.kevlindev.parsing.Lexer;
import com.kevlindev.parsing.Parser;
import com.kevlindev.pinconverter.model.Board;
import com.kevlindev.pinconverter.model.Bus;
import com.kevlindev.pinconverter.model.Pin;
import com.kevlindev.utils.StringUtils;

/**
 * BoardDefinitionParser
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class BoardDefinitionParser extends Parser<BoardDefinitionTokenType> {
	/**
	 * A set used when determining if a given token can be considered a name
	 */
	// @formatter:off
	private static Set<BoardDefinitionTokenType> NAME_SET =
		EnumSet.of(
			BoardDefinitionTokenType.IDENTIFIER,
			BoardDefinitionTokenType.STRING,
			BoardDefinitionTokenType.NUMBER
		);
	// @formatter:on

	/**
	 * The list of boards created during the parse
	 */
	private List<Board> boards;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.parsing.Parser#createLexer()
	 */
	protected Lexer<BoardDefinitionTokenType> createLexer() {
		return new BoardDefinitionLexer();
	}

	/**
	 * getBoards
	 * 
	 * @return List<Board>
	 */
	public List<Board> getBoards() {
		return boards;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kevlindev.parsing.IParser#parse(java.lang.String)
	 */
	public void parse(String source) {
		// clear boards
		boards = Collections.emptyList();

		// setup lexer and prime
		getLexer().setSource(source);
		advance();

		try {
			boards = parseBoardDefinitions();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Assignments : Assignments Assignment
	 * 
	 * @return
	 * @throws ParseException
	 */
	protected List<Tuple<String, String>> parseAssignments() throws ParseException {
		List<Tuple<String, String>> assignments = new ArrayList<Tuple<String, String>>();

		parseAssignment(assignments);

		while (isType(BoardDefinitionTokenType.COMMA)) {
			// advance over ','
			advance();

			parseAssignment(assignments);
		}

		return assignments;
	}

	/**
	 * Assignment : Name | Name EQUAL Name
	 * 
	 * @param assignments
	 * @throws ParseException
	 */
	protected void parseAssignment(List<Tuple<String, String>> assignments) throws ParseException {
		if (isType(NAME_SET)) {
			String name = parseName();
			String value = null;

			if (isType(BoardDefinitionTokenType.EQUAL)) {
				// advance over '='
				advance();

				value = parseName();
			}

			assignments.add(new Tuple<String, String>(name, value));
		} else {
			throwError("An assignment must begin with a valid name type");
		}
	}

	/**
	 * Board : Names LCURLY Buses RCURLY;<br>
	 * Board : Names COLON Name LCURLY Buses RCURLY;<br>
	 * Board : Names LCURLY Pragmas Buses RCURLY;<br>
	 * Board : Names COLON Name LCURLY Pragmas Buses RCURLY;<br>
	 * Pragmas : Header | Config | Header Config;<br>
	 * 
	 * @throws ParseException
	 * @return Board
	 */
	protected Board parseBoardDefinition() throws ParseException {
		Board result = new Board();

		for (String name : parseNames()) {
			result.addName(name);
		}

		if (isType(BoardDefinitionTokenType.COLON)) {
			// advance over ':'
			advance();

			result.setParentBoardName(parseName());
		}

		if (isType(BoardDefinitionTokenType.LCURLY)) {
			// advance over '{'
			advance();

			if (isType(BoardDefinitionTokenType.HEADER)) {
				result.setHeader(parseHeader());
			}

			if (isType(BoardDefinitionTokenType.CONFIG)) {
				result.setConfigs(parseConfigs());
			}

			for (Bus bus : parseBuses()) {
				result.addBus(bus);
			}

			if (isType(BoardDefinitionTokenType.RCURLY)) {
				// advance over '}'
				advance();
			} else {
				throwError("A board definition should end with a right curly brace");
			}
		} else {
			throwError("A board definition should start with a left curly brace");
		}

		return result;
	}

	/**
	 * BoardDefinitions : Boards;<br>
	 * Boards : Boards Board | Board;
	 * 
	 * @throws ParseException
	 * @return List<Board>
	 */
	protected List<Board> parseBoardDefinitions() throws ParseException {
		List<Board> result = new ArrayList<Board>();
		ILexer<BoardDefinitionTokenType> lexer = getLexer();

		while (lexer.hasNext()) {
			Lexeme<BoardDefinitionTokenType> current = getCurrentLexeme();

			result.add(parseBoardDefinition());

			// break possible infinite loop if the lexer didn't advance
			if (current == getCurrentLexeme()) { // $codepro.audit.disable
													// useEquals
				break;
			}
		}

		return result;
	}

	/**
	 * Buses : Buses Bus | Bus;<br>
	 * Bus : Name COLON FpgaPins;<br>
	 * Bus : Name LCURLY Names RCURLY COLON FpgaPins;
	 * 
	 * @throws ParseException
	 * @return List<Bus>
	 */
	protected List<Bus> parseBuses() throws ParseException {
		List<Bus> result = new ArrayList<Bus>();

		while (isType(NAME_SET)) {
			Bus bus = new Bus(parseName());

			if (isType(BoardDefinitionTokenType.LCURLY)) {
				// advance over '{'
				advance();

				Map<String, String> additions = new LinkedHashMap<String, String>();

				for (Tuple<String, String> addition : parseAssignments()) {
					additions.put(addition.first, addition.last);
				}

				if (isType(BoardDefinitionTokenType.RCURLY)) {
					// advance over '}'
					advance();

					bus.setAdditions(additions);
				} else {
					throwError("A right curly brace must close bus additions");
				}
			}

			if (isType(BoardDefinitionTokenType.COLON)) {
				// advance over ':'
				advance();

				bus.setPins(parseFpgaPins());
			}

			result.add(bus);
		}

		return result;
	}

	/**
	 * Config : CONFIG LCURLY Names RCURLY;<br>
	 * 
	 * @return
	 * @throws ParseException
	 */
	protected List<Tuple<String, String>> parseConfigs() throws ParseException {
		List<Tuple<String, String>> result = null;

		if (isType(BoardDefinitionTokenType.CONFIG)) {
			// advance over '@config'
			advance();

			if (isType(BoardDefinitionTokenType.LCURLY)) {
				// advance over '{'
				advance();

				result = parseAssignments();

				if (isType(BoardDefinitionTokenType.RCURLY)) {
					// advance over '}'
					advance();
				} else {
					throwError("A configuration block must end with '}'");
				}
			} else {
				throwError("A configuration block must start with '{'");
			}
		}

		return result;
	}

	/**
	 * FpgaPin : Name | Name LCURLY Names RCURLY;
	 * 
	 * @throws ParseException
	 * @return Pin
	 */
	protected Pin parseFpgaPin() throws ParseException {
		Pin result = new Pin();

		// Pin name is optional. Empty names are used for non-connected pins
		if (isType(NAME_SET)) {
			result.setName(parseName());
		}

		if (isType(BoardDefinitionTokenType.LCURLY)) {
			// skip over '{'
			advance();

			Map<String, String> additions = new LinkedHashMap<String, String>();

			for (Tuple<String, String> addition : parseAssignments()) {
				additions.put(addition.first, addition.last);
			}

			if (isType(BoardDefinitionTokenType.RCURLY)) {
				// advance over '}'
				advance();

				result.setAdditions(additions);
			} else {
				throwError("A right curly brace must close pin additions");
			}
		}

		return result;
	}

	/**
	 * FpgaPins: FpgaPins COMMA FpgaPin | FpgaPin;
	 * 
	 * @throws ParseException
	 * @return List<Pin>
	 */
	protected List<Pin> parseFpgaPins() throws ParseException {
		List<Pin> pins = new ArrayList<Pin>();

		pins.add(parseFpgaPin());

		while (isType(BoardDefinitionTokenType.COMMA)) {
			// skip over ','
			advance();

			pins.add(parseFpgaPin());
		}

		return pins;
	}

	/**
	 * Header : HEADER MULTILINE_STRING;<br>
	 * 
	 * @return
	 * @throws ParseException
	 */
	protected String parseHeader() throws ParseException {
		String result = null;

		if (isType(BoardDefinitionTokenType.HEADER)) {
			// advance over '@header'
			advance();

			if (isType(BoardDefinitionTokenType.MULTILINE_STRING)) {
				String text = getText();

				// trim lines and set header
				result = StringUtils.trimLines(text.substring(2, text.length() - 2).trim());

				// advance over multi-line string
				advance();
			} else {
				throwError("Expected multi-line string after @header");
			}
		}

		return result;
	}

	/**
	 * Name : IDENTIFIER | STRING;
	 * 
	 * @throws ParseException
	 * @return String
	 */
	protected String parseName() throws ParseException {
		String result = null;

		if (isType(NAME_SET)) {
			result = getText();

			if (isType(BoardDefinitionTokenType.STRING)) {
				result = result.substring(1, result.length() - 1);
			}

			// advance over name
			advance();
		} else {
			throwError("Expected an identifier or string");
		}

		return result;
	}

	/**
	 * Names : Names COMMA Name | Name;
	 * 
	 * @throws ParseException
	 * @return List<String>
	 */
	protected List<String> parseNames() throws ParseException {
		List<String> result = new ArrayList<String>();

		result.add(parseName());

		while (isType(BoardDefinitionTokenType.COMMA)) {
			// advance over ','
			advance();

			result.add(parseName());
		}

		return result;
	}
}
