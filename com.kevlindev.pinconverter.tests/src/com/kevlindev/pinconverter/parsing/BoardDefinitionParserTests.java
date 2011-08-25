/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.parsing;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import com.kevlindev.pinconverter.model.Board;
import com.kevlindev.utils.IOUtils;

/**
 * BoardDefinitionParserTests
 */
public class BoardDefinitionParserTests {
	@Test
	public void test() {
		BoardDefinitionParser parser = new BoardDefinitionParser();
		InputStream is = BoardDefinitionLexerTests.class.getResourceAsStream("boards.txt");
		String source = IOUtils.getString(is);

		parser.parse(source);

		List<Board> boards = parser.getBoards();
		assertNotNull(boards);

		System.out.println(boards);
	}
}
