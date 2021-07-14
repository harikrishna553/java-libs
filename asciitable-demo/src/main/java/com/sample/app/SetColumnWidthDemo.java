package com.sample.app;

import de.vandermeer.asciitable.AsciiTable;

public class SetColumnWidthDemo {

	public static void main(String args[]) {
		AsciiTable asciiTable = new AsciiTable();

		asciiTable.addRule();
		asciiTable.addRow("row 1 col 1", "row 1 col 2");
		asciiTable.addRule();
		asciiTable.addRow("row 2 col 1", "row 2 col 2");
		asciiTable.addRule();

		System.out.println("Set column width to 50");
		String rend = asciiTable.render(50);
		System.out.println(rend);

		System.out.println("\nSet column width to 40");
		rend = asciiTable.render(40);
		System.out.println(rend);

		System.out.println("\nSet column width to 30");
		rend = asciiTable.render(30);
		System.out.println(rend);

		System.out.println("\nSet column width to 20");
		rend = asciiTable.render(20);
		System.out.println(rend);

		System.out.println("\nSet column width to 10");
		rend = asciiTable.render(10);
		System.out.println(rend);

	}

}
