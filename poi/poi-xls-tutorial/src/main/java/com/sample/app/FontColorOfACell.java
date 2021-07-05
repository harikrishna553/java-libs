package com.sample.app;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class FontColorOfACell {

	public static void main(String args[]) {
		String xlsFileName = "/Users/Shared/poi/xls/abc_org.xls";

		try (Workbook workbook = new HSSFWorkbook(); OutputStream os = new FileOutputStream(xlsFileName)) {

			Sheet employeeSheet = workbook.createSheet("employees");
			employeeSheet.setDefaultColumnWidth(50);

			Row row = employeeSheet.createRow(1);

			Cell empIdCell = row.createCell(0);
			empIdCell.setCellValue("1");

			CellStyle style = workbook.createCellStyle();
			style.setWrapText(true);
			Font font = workbook.createFont();
			font.setBold(true);
			font.setFontHeightInPoints((short) 15);
			font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
			style.setFont(font);
			empIdCell.setCellStyle(style);

			Cell empNameCell = row.createCell(1);
			empNameCell.setCellValue("Krishna");

			CellStyle style1 = workbook.createCellStyle();
			style1.setWrapText(true);
			Font font1 = workbook.createFont();
			font1.setFontHeightInPoints((short) 15);
			font1.setBold(true);
			font1.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
			style1.setFont(font1);
			empNameCell.setCellStyle(style1);

			workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
