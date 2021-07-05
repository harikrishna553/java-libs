package com.sample.app;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class StyleCellBorders {
	public static void main(String args[]) {
		String xlsFileName = "/Users/Shared/poi/xls/abc_org.xls";

		try (Workbook workbook = new HSSFWorkbook(); OutputStream os = new FileOutputStream(xlsFileName)) {
			CellStyle style = workbook.createCellStyle();
			style.setWrapText(true);
			style.setAlignment(HorizontalAlignment.LEFT);
			style.setVerticalAlignment(VerticalAlignment.CENTER);

			style.setBorderBottom(BorderStyle.MEDIUM_DASH_DOT);
			style.setBorderLeft(BorderStyle.THICK);
			style.setBorderRight(BorderStyle.THIN);
			style.setBorderTop(BorderStyle.MEDIUM_DASH_DOT);

			style.setBottomBorderColor(IndexedColors.RED.index);
			style.setLeftBorderColor(IndexedColors.GREEN.index);
			style.setRightBorderColor(IndexedColors.BLUE.index);
			style.setTopBorderColor(IndexedColors.BLACK.index);

			Font defaultFont = workbook.createFont();
			defaultFont.setBold(true);
			defaultFont.setFontHeight((short) 300);
			style.setFont(defaultFont);

			Sheet employeeSheet = workbook.createSheet("employees");
			employeeSheet.setColumnWidth(2, 5000);

			// Add employee row
			Row row = employeeSheet.createRow(1);
			row.setHeight((short) 500);

			Cell empNameCell = row.createCell(2);
			empNameCell.setCellStyle(style);
			empNameCell.setCellValue("Krishna");

			workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
