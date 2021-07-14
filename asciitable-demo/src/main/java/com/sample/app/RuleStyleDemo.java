package com.sample.app;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a8.A8_Grids;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

public class RuleStyleDemo {

	public static void main(String args[]) {

		AsciiTable asciiTable = new AsciiTable();

		asciiTable.addLightRule();
		asciiTable.addRow("Row 1 col 1", "Row 1 col 2");

		asciiTable.addRule();
		asciiTable.addRow("Row 2 col 1", "Row 2 col 2");

		asciiTable.addHeavyRule();
		asciiTable.addRow("Row 3 col 1", "Row 3 col 2");
		asciiTable.addStrongRule();

		asciiTable.setTextAlignment(TextAlignment.CENTER);

		asciiTable.getContext().setGrid(A8_Grids.lineDoubleBlocks());

		String rend = asciiTable.render(35);
		System.out.println(rend);

	}

}