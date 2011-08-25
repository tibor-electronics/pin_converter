/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.text;

import java.util.ArrayList;
import java.util.List;

import com.kevlindev.utils.StringUtils;

/**
 * TableRow
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class TableRow {
	private List<String> columns;

	/**
	 * TableRow
	 * 
	 * @param columns
	 */
	public TableRow(String... columns) {
		addColumns(columns);
	}

	/**
	 * addColumn
	 * 
	 * @param columns
	 */
	public void addColumns(String... columns) {
		if (this.columns == null) {
			this.columns = new ArrayList<String>();
		}

		for (String column : columns) {
			this.columns.add(column);
		}
	}

	/**
	 * getColumn
	 * 
	 * @param index
	 * @return String
	 */
	public String getColumn(int index) {
		return (columns != null && 0 <= index && index < columns.size()) ? columns.get(index) : StringUtils.EMPTY;
	}

	/**
	 * getColumnWidths
	 * 
	 * @return List<Integer>
	 */
	public List<Integer> getColumnWidths() {
		List<Integer> result = new ArrayList<Integer>();

		if (columns != null) {
			for (String column : columns) {
				int maxLength = 0;
				
				for (String line : StringUtils.EOL_PATTERN.split(column)) {
					maxLength = Math.max(maxLength, line.length());
				}
				
				result.add(maxLength);
			}
		}

		return result;
	}

	/**
	 * size
	 * 
	 * @return int
	 */
	public int size() {
		int result = 0;

		if (columns != null) {
			result = columns.size();
		}

		return result;
	}
}
