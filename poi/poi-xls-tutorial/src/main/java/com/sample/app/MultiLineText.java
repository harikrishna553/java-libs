package com.sample.app;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class MultiLineText {
	public static void main(String args[]) {
		String xlsFileName = "/Users/Shared/poi/xls/abc_org.xls";

		try (Workbook workbook = new HSSFWorkbook(); OutputStream os = new FileOutputStream(xlsFileName)) {

			String text = "Java is a high-level programming language.\nJava is easy to learn.";

			Sheet sheet = workbook.createSheet("test");
			sheet.setColumnWidth(2, 10000);
			sheet.autoSizeColumn(2);

			Row row = sheet.createRow(1);
			row.setHeightInPoints(50);

			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setWrapText(true);

			Cell cell = row.createCell(2);
			cell.setCellValue(text);
			cell.setCellStyle(cellStyle);

			workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
