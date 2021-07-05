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

public class SetActiveSheetDemo {
	public static void main(String args[]) {
		String xlsFileName = "/Users/Shared/poi/xls/abc_org.xls";
		
		try (Workbook workbook = new HSSFWorkbook(); OutputStream os = new FileOutputStream(xlsFileName)) {
			CellStyle style = workbook.createCellStyle();
			style.setWrapText(true);
			style.setAlignment(HorizontalAlignment.LEFT);
			style.setVerticalAlignment(VerticalAlignment.CENTER);

			Font defaultFont = workbook.createFont();
			defaultFont.setBold(true);
			defaultFont.setFontHeight((short) 300);
			style.setFont(defaultFont);

			Sheet employeeSheet = workbook.createSheet("employees");
			employeeSheet.setColumnWidth(0, 1000);
			employeeSheet.setColumnWidth(1, 10000);

			// Add colunmn headers
			Row row1 = employeeSheet.createRow(0);
			row1.setHeight((short) 800);

			Cell empIdHeader = row1.createCell(0);
			empIdHeader.setCellValue("id");
			empIdHeader.setCellStyle(style);

			Cell empNameHeader = row1.createCell(1);
			empNameHeader.setCellValue("name");
			empNameHeader.setCellStyle(style);

			// Add employee row
			Row row2 = employeeSheet.createRow(1);
			row2.setHeight((short) 800);

			Cell empIdCell = row2.createCell(0);
			empIdCell.setCellValue("1");
			empIdCell.setCellStyle(style);

			Cell empNameCell = row2.createCell(1);
			empNameCell.setCellValue("Krishna Gurram");
			empNameCell.setCellStyle(style);

			workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
