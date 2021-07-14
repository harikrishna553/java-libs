package com.sample.app;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

public class SetPaddingDemo {

	public static void main(String args[]) {

		AsciiTable asciiTable = new AsciiTable();

		asciiTable.addRule();
		asciiTable.addRow("Row 1 col 1", "Row 1 col 2");

		asciiTable.addRule();
		asciiTable.addRow("Row 2 col 1", "Row 2 col 2");

		asciiTable.addRule();
		asciiTable.addRow("Row 3 col 1", "Row 3 col 2");
		asciiTable.addRule();
		
		asciiTable.setTextAlignment(TextAlignment.CENTER);

		asciiTable.setPaddingLeftChar('>');
		asciiTable.setPaddingRightChar('=');
		asciiTable.setPaddingTopChar('^');
		asciiTable.setPaddingBottomChar('<');
		asciiTable.setPadding(1);

		String rend = asciiTable.render(35);
		System.out.println(rend);

	}

}