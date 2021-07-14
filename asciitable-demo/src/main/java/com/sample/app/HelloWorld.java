package com.sample.app;

import de.vandermeer.asciitable.AsciiTable;

public class HelloWorld {

	public static void main(String args[]) {
		AsciiTable asciiTable = new AsciiTable();

		asciiTable.addRule();
		asciiTable.addRow("row 1 col 1", "row 1 col 2");
		asciiTable.addRule();
		asciiTable.addRow("row 2 col 1", "row 2 col 2");
		asciiTable.addRule();
		
		String rend = asciiTable.render();
				
		System.out.println(rend);

	}

}


