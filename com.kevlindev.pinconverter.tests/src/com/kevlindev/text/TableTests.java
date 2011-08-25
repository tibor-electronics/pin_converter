/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.text;

//import static org.junit.Assert.*;

import org.junit.Test;

/**
 * TableTests
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class TableTests {
	@Test
	public void test() {
		Table table = new Table();

		// set padding
		table.setPadding(1);
		
		// set alignment
		table.setAlignment(2, Table.Alignment.RIGHT);
		
		// set headers
		table.addHeaders("id");
		table.addHeaders("description");
		table.addHeaders("amount");

		// create row 1
		TableRow row1 = new TableRow();
		row1.addColumns("abc");
		row1.addColumns("This is a longer column");
		row1.addColumns("123");

		// create row 2
		TableRow row2 = new TableRow();
		row2.addColumns("d");
		row2.addColumns("short column");
		row2.addColumns("4");

		// create row 3
		TableRow row3 = new TableRow();
		row3.addColumns("efgh");
		row3.addColumns("This is a slightly longer column");
		row3.addColumns("5678");

		table.addRow(row1);
		table.addRow(row2);
		table.addRow(row3);

		// toString
		System.out.println(table.toString());
	}
}
