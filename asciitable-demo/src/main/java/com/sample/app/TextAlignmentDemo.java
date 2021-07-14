package com.sample.app;

import de.vandermeer.asciitable.AT_Row;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

public class TextAlignmentDemo {

	public static void main(String args[]) {
		String str = "No one can tell me,\n" + "Nobody knows,\n" + "Where the wind comes from,\n"
				+ "Where the wind goes.\n";

		AsciiTable asciiTable = new AsciiTable();

		asciiTable.addRule();
		AT_Row row = asciiTable.addRow(str, str, str, str, str, str);

		row.getCells().get(0).getContext().setTextAlignment(TextAlignment.LEFT);
		row.getCells().get(1).getContext().setTextAlignment(TextAlignment.RIGHT);
		row.getCells().get(2).getContext().setTextAlignment(TextAlignment.CENTER);
		row.getCells().get(3).getContext().setTextAlignment(TextAlignment.JUSTIFIED);
		row.getCells().get(4).getContext().setTextAlignment(TextAlignment.JUSTIFIED_LEFT);
		row.getCells().get(5).getContext().setTextAlignment(TextAlignment.JUSTIFIED_RIGHT);
		
		asciiTable.addRule();

		String rend = asciiTable.render();
		System.out.println(rend);

	}

}
