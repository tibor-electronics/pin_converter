/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.pinconverter.parsing;

import java.io.InputStream;

import org.junit.Test;

import com.kevlindev.parsing.Lexeme;
import com.kevlindev.utils.IOUtils;

/**
 * BoardDefinitionLexerTests
 */
public class BoardDefinitionLexerTests {
	@Test
	public void test() {
		InputStream is = BoardDefinitionLexerTests.class.getResourceAsStream("boards.txt");
		String source = IOUtils.getString(is);
		BoardDefinitionLexer lexer = new BoardDefinitionLexer();

		lexer.setSource(source);

		while (lexer.hasNext()) {
			Lexeme<BoardDefinitionTokenType> lexeme = lexer.advance();

			System.out.println(lexeme);
		}
	}
}
