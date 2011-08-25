/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * IOUtils
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class IOUtils {
	/**
	 * IOUtils
	 */
	private IOUtils() {
	}

	/**
	 * Based on http://stackoverflow.com/questions/309424/in-java-how-do-a-read-convert-an-inputstream-in-to-a-string/309718#309718
	 * 
	 * @param is
	 * @return a UTF-8 string of the input stream
	 */
	public static String getString(InputStream is) {
		char[] buffer = new char[0x10000];
		StringBuilder out = new StringBuilder();

		try {
			Reader in = new InputStreamReader(is, "UTF-8");
			int read;

			do {
				read = in.read(buffer, 0, buffer.length);

				if (read > 0) {
					out.append(buffer, 0, read);
				}
			} while (read >= 0);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) { // $codepro.audit.disable emptyCatchClause
			}
		}

		return out.toString();
	}
}
