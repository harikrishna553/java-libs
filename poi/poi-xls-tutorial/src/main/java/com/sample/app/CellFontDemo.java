package com.sample.app;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class CellFontDemo {
	public static void main(String args[]) {
		String xlsFileName = "/Users/Shared/poi/xls/abc_org.xls";

		try (Workbook workbook = new HSSFWorkbook(); OutputStream os = new FileOutputStream(xlsFileName)) {

			Sheet employeeSheet = workbook.createSheet("employees");
			employeeSheet.setColumnWidth(2, 5000);
			employeeSheet.setColumnWidth(3, 8000);

			Row row = employeeSheet.createRow(1);
			row.setHeight((short) 500);

			Cell empNameCell = row.createCell(2);
			empNameCell.setCellValue("Krishna");
			
			Font defaultFont = workbook.createFont();
			defaultFont.setBold(true);
			defaultFont.setFontHeightInPoints((short) 25);

			CellStyle style = workbook.createCellStyle();
			style.setWrapText(true);
			style.setAlignment(HorizontalAlignment.LEFT);
			style.setVerticalAlignment(VerticalAlignment.CENTER);
			style.setFont(defaultFont);

			empNameCell.setCellStyle(style);

			workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
