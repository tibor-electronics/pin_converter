/**
 * Copyright 2011, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.kevlindev.utils.StringUtils;

/**
 * Table
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */

public class Table {
	public enum Alignment {
		/**
		 * Align content to the left in the column, filling the remaining column
		 * with spaces
		 */
		LEFT,

		/**
		 * Align the content in the center of the column, filling to the left
		 * and right with spaces
		 */
		CENTER,

		/**
		 * Align content to the right in the column, filling the left with
		 * spaces
		 */
		RIGHT,

		/**
		 * No alignment. Just print the content with padding
		 */
		COLLAPSE;
	}

	/**
	 * A collection of table rows, possibly null
	 */
	private List<TableRow> rows;

	/**
	 * A collection of column headers to be printed with the table. This value
	 * may be null.
	 */
	private List<String> headers;

	/**
	 * A collection of column alignments. This value may be null.
	 */
	private List<Alignment> alignments;

	/**
	 * The width of each column in this table
	 */
	private List<Integer> columnWidths;

	/**
	 * The number of spaces to print between columns
	 */
	private int padding = 1;

	/**
	 * addHeaders
	 * 
	 * @param headers
	 */
	public void addHeaders(String... headers) {
		if (this.headers == null) {
			this.headers = new ArrayList<String>();
		}

		for (String header : headers) {
			this.headers.add(header);
		}
	}

	/**
	 * addRow
	 * 
	 * @param row
	 */
	public void addRow(TableRow row) {
		if (rows == null) {
			rows = new ArrayList<TableRow>();
		}

		rows.add(row);
	}

	/**
	 * addRow
	 * 
	 * @param columns
	 */
	public void addRow(String... columns) {
		if (columns != null && columns.length > 0) {
			this.addRow(new TableRow(columns));
		}
	}

	/**
	 * getColumnWidths
	 * 
	 * @return List<Integer>
	 */
	public List<Integer> getColumnWidths() {
		if (columnWidths == null) {
			List<Integer> result = new ArrayList<Integer>();

			if (rows != null) {
				if (headers != null) {
					for (String header : headers) {
						result.add(header.length());
					}
				}

				for (TableRow row : rows) {
					List<Integer> widths = row.getColumnWidths();

					int length = Math.max(result.size(), widths.size());

					for (int i = 0; i < length; i++) {
						int width1 = (i < result.size()) ? result.get(i) : widths.get(i);
						int width2 = (i < widths.size()) ? widths.get(i) : result.get(i);
						int max = Math.max(width1, width2);

						if (i < result.size()) {
							result.set(i, max);
						} else {
							result.add(max);
						}
					}
				}
			}

			columnWidths = result;
		}

		return columnWidths;
	}

	/**
	 * getPadding
	 * 
	 * @return int
	 */
	public int getPadding() {
		return padding;
	}

	/**
	 * getRow
	 * 
	 * @param index
	 * @return TableRow
	 */
	public TableRow getRow(int index) {
		return (rows != null && 0 <= index && index < rows.size()) ? rows.get(index) : null;
	}

	/**
	 * setAlignment
	 * 
	 * @param index
	 * @param alignment
	 */
	public void setAlignment(int index, Alignment alignment) {
		if (alignments == null) {
			alignments = new ArrayList<Alignment>();
		}

		while (alignments.size() <= index) {
			alignments.add(Alignment.LEFT);
		}

		alignments.set(index, alignment);
	}

	/**
	 * setColumnWidth
	 * 
	 * @param index
	 * @param width
	 */
	public void setColumnWidth(int index, int width) {
		if (columnWidths == null) {
			columnWidths = new ArrayList<Integer>();
		}

		while (columnWidths.size() <= index) {
			columnWidths.add(0);
		}

		columnWidths.set(index, width);
	}

	/**
	 * setPadding
	 * 
	 * @param padding
	 */
	public void setPadding(int padding) {
		this.padding = padding;
	}

	/**
	 * Set width of table, reducing the size of the last column as necessary.
	 * The last column size will not go below minColumnWidth
	 * 
	 * @param width
	 * @param minColumnWidth
	 */
	public void setWidth(int width, int minColumnWidth) {
	}

	/**
	 * Set width of table, reducing the size of the specified column as
	 * necessary. The column size will not go below minColumnWidth
	 * 
	 * @param width
	 * @param minColumnWidth
	 * @param column
	 */
	public void setWidth(int width, int minColumnWidth, int column) {
		List<Integer> columnWidths = this.getColumnWidths();

		if (0 <= column && column < columnWidths.size()) {
			int columnWidth = width;

			for (int i = 0; i < columnWidths.size(); i++) {
				if (i != column) {
					columnWidth -= (columnWidths.get(i) + this.getPadding());
				}
			}

			this.setColumnWidth(column, Math.max(columnWidth, minColumnWidth));
		}
	}

	/**
	 * sort
	 * 
	 * @param index
	 * @param comparator
	 */
	public void sort(final int index) {
		if (rows != null) {
			Collections.sort(rows, new Comparator<TableRow>() {
				@Override
				public int compare(TableRow o1, TableRow o2) {
					return o1.getColumn(index).compareToIgnoreCase(o2.getColumn(index));
				}
			});
		}
	}

	/**
	 * sort
	 * 
	 * @param index
	 * @param comparator
	 */
	public void sort(final int index, final Comparator<String> comparator) {
		if (rows != null) {
			Collections.sort(rows, new Comparator<TableRow>() {
				@Override
				public int compare(TableRow o1, TableRow o2) {
					return comparator.compare(o1.getColumn(index), o2.getColumn(index));
				}
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();

		if (rows != null) {
			List<Integer> columnWidths = getColumnWidths();
			List<String> formats = new ArrayList<String>(columnWidths.size());
			String pad = (padding > 0) ? StringUtils.repeatString(" ", padding) : StringUtils.EMPTY;

			for (int i = 0; i < columnWidths.size(); i++) {
				Alignment alignment = (alignments != null && i < alignments.size()) ? alignments.get(i) : Alignment.LEFT;

				switch (alignment) {
					case LEFT:
						formats.add("%-" + columnWidths.get(i) + "s");
						break;

					case CENTER:
						// TODO: not supported. Left-justify for now
						formats.add("%-" + columnWidths.get(i) + "s");
						break;

					case RIGHT:
						formats.add("%" + columnWidths.get(i) + "s");
						break;

					case COLLAPSE:
						formats.add("%s");
						break;
				}
			}

			if (headers != null) {
				for (int i = 0; i < headers.size(); i++) {
					String format = "%-" + columnWidths.get(i) + "s";
					buffer.append(String.format(format, headers.get(i)));
					buffer.append(pad);
				}

				buffer.setLength(buffer.length() - pad.length());
				buffer.append(StringUtils.EOL);

				for (int i = 0; i < headers.size(); i++) {
					buffer.append(StringUtils.repeatString("=", columnWidths.get(i)));
					buffer.append(pad);
				}

				buffer.setLength(buffer.length() - pad.length());
				buffer.append(StringUtils.EOL);
			}

			for (TableRow row : rows) {
				List<List<String>> columns = new ArrayList<List<String>>();
				int maxLength = 0;

				for (int i = 0; i < row.size(); i++) {
					String column = row.getColumn(i);
					int width = columnWidths.get(i);
					List<String> lines = StringUtils.wrap(column, width);

					columns.add(lines);

					maxLength = Math.max(maxLength, lines.size());
				}

				for (int i = 0; i < maxLength; i++) {
					for (int j = 0; j < columns.size(); j++) {
						List<String> column = columns.get(j);
						String text = (column.size() > 0) ? column.remove(0) : StringUtils.EMPTY;

						buffer.append(String.format(formats.get(j), text));
						buffer.append(pad);
					}

					buffer.setLength(buffer.length() - pad.length());
					buffer.append(StringUtils.EOL);
				}
			}
		}

		return buffer.toString();
	}
}
