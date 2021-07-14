package com.sample.app;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a7.A7_Grids;
import de.vandermeer.asciithemes.a8.A8_Grids;
import de.vandermeer.asciithemes.u8.U8_Grids;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

public class GridThemeDemo {

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

		System.out.println("\nA7_Grids -> minusBarPlusEquals");
		asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals());
		String rend = asciiTable.render(35);
		System.out.println(rend);

		System.out.println("\nA7_Grids -> minusBarPlus");
		asciiTable.getContext().setGrid(A7_Grids.minusBarPlus());
		rend = asciiTable.render(35);
		System.out.println(rend);

		System.out.println("\nA8_Grids -> lineDoubleBlocks");
		asciiTable.getContext().setGrid(A8_Grids.lineDoubleBlocks());
		rend = asciiTable.render(35);
		System.out.println(rend);

		System.out.println("\nA8_Grids -> lineDobuleTripple");
		asciiTable.getContext().setGrid(A8_Grids.lineDobuleTripple());
		rend = asciiTable.render(35);
		System.out.println(rend);

		System.out.println("\nU8_Grids -> borderDoubleLight");
		asciiTable.getContext().setGrid(U8_Grids.borderDoubleLight());
		rend = asciiTable.render(35);
		System.out.println(rend);

		System.out.println("\nU8_Grids -> borderDouble");
		asciiTable.getContext().setGrid(U8_Grids.borderDouble());
		rend = asciiTable.render(35);
		System.out.println(rend);

		System.out.println("\nU8_Grids -> borderheavy");
		asciiTable.getContext().setGrid(U8_Grids.borderheavy());
		rend = asciiTable.render(35);
		System.out.println(rend);

		System.out.println("\nU8_Grids -> borderLight");
		asciiTable.getContext().setGrid(U8_Grids.borderLight());
		rend = asciiTable.render(35);
		System.out.println(rend);

		System.out.println("\nU8_Grids -> borderLightDouble");
		asciiTable.getContext().setGrid(U8_Grids.borderLightDouble());
		rend = asciiTable.render(35);
		System.out.println(rend);

		System.out.println("\nU8_Grids -> borderStrongDoubleLight");
		asciiTable.getContext().setGrid(U8_Grids.borderStrongDoubleLight());
		rend = asciiTable.render(35);
		System.out.println(rend);

		System.out.println("\nU8_Grids -> borderLight");
		asciiTable.getContext().setGrid(U8_Grids.borderLight());
		rend = asciiTable.render(35);
		System.out.println(rend);

		System.out.println("\nU8_Grids -> borderDoubleLight");
		asciiTable.getContext().setGrid(U8_Grids.borderDoubleLight());
		rend = asciiTable.render(35);
		System.out.println(rend);

		System.out.println("\nU8_Grids -> borderheavy");
		asciiTable.getContext().setGrid(U8_Grids.borderheavy());
		rend = asciiTable.render(35);
		System.out.println(rend);

		System.out.println("\nU8_Grids -> borderDouble");
		asciiTable.getContext().setGrid(U8_Grids.borderDouble());
		rend = asciiTable.render(35);
		System.out.println(rend);

		System.out.println("\nU8_Grids -> borderLightDouble");
		asciiTable.getContext().setGrid(U8_Grids.borderLightDouble());
		rend = asciiTable.render(35);
		System.out.println(rend);

		System.out.println("\nU8_Grids -> borderStrongDoubleLight");
		asciiTable.getContext().setGrid(U8_Grids.borderStrongDoubleLight());
		rend = asciiTable.render(35);
		System.out.println(rend);
	}

}