package com.sample.app;

import de.vandermeer.asciitable.AsciiTable;

public class ColumnSpanDemo {

	public static void main(String args[]) {
		AsciiTable asciiTable = new AsciiTable();

		asciiTable.addRule();
		asciiTable.addRow("row 1 col 1", "row 1 col 2", "row 1 col 3", "row 1 col 4", "row 1 col 5");

		asciiTable.addRule();
		asciiTable.addRow("row 2 col 1", null, null, null, "Span 4 columns");

		asciiTable.addRule();
		asciiTable.addRow(null, "Span 2 columns", null, null, "Span 3 columns");

		asciiTable.addRule();
		asciiTable.addRow(null, null, null, null, "Span 5 columns");
		asciiTable.addRule();

		String rend = asciiTable.render();

		System.out.println(rend);

	}

}
