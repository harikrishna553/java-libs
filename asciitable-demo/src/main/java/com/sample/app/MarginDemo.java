package com.sample.app;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

public class MarginDemo {

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

		asciiTable.getContext().setFrameLeftChar('>');
		asciiTable.getContext().setFrameRightChar('<');
		asciiTable.getContext().setFrameBottomChar('v');
		asciiTable.getContext().setFrameTopChar('^');

		asciiTable.getContext().setFrameLeftMargin(3);
		asciiTable.getContext().setFrameRightMargin(4);
		asciiTable.getContext().setFrameBottomMargin(2);
		asciiTable.getContext().setFrameTopMargin(1);

		String rend = asciiTable.render(35);
		System.out.println(rend);

	}

}