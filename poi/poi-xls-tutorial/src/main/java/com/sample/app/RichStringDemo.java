package com.sample.app;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class RichStringDemo {
	public static void main(String args[]) {
		String xlsFileName = "/Users/Shared/poi/xls/abc_org.xls";

		try (Workbook workbook = new HSSFWorkbook(); OutputStream os = new FileOutputStream(xlsFileName)) {

			HSSFRichTextString richString = new HSSFRichTextString("Hello, World!");

			HSSFFont font1 = (HSSFFont) workbook.createFont();
			font1.setBold(true);
			font1.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
			font1.setFontHeightInPoints((short) 25);

			HSSFFont font2 = (HSSFFont) workbook.createFont();
			font2.setItalic(true);
			font2.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
			font2.setFontHeightInPoints((short) 30);

			richString.applyFont(0, 6, font1);
			richString.applyFont(6, 13, font2);

			Sheet employeeSheet = workbook.createSheet("employees");
			employeeSheet.setColumnWidth(2, 5000);

			Row row = employeeSheet.createRow(1);
			row.setHeightInPoints(50);

			Cell cell = row.createCell(2);

			cell.setCellValue(richString);

			workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
