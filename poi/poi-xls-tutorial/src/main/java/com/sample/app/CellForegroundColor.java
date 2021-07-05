package com.sample.app;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class CellForegroundColor {
	public static void main(String args[]) {
		String xlsFileName = "/Users/Shared/poi/xls/abc_org.xls";

		try (Workbook workbook = new HSSFWorkbook(); OutputStream os = new FileOutputStream(xlsFileName)) {

			Sheet employeeSheet = workbook.createSheet("employees");
			employeeSheet.setColumnWidth(2, 5000);
			employeeSheet.setColumnWidth(3, 5000);

			Row row = employeeSheet.createRow(1);
			row.setHeight((short) 500);

			Cell empNameCell = row.createCell(2);
			empNameCell.setCellValue("Krishna");

			CellStyle style = workbook.createCellStyle();
			style.setWrapText(true);
			style.setAlignment(HorizontalAlignment.LEFT);
			style.setVerticalAlignment(VerticalAlignment.CENTER);

			style.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			Font defaultFont = workbook.createFont();
			defaultFont.setBold(true);
			defaultFont.setFontHeight((short) 300);
			defaultFont.setColor(IndexedColors.WHITE.getIndex());

			style.setFont(defaultFont);

			empNameCell.setCellStyle(style);

			// foreground color
			Cell empLastNameCell = row.createCell(3);
			empLastNameCell.setCellValue("Gurram");

			CellStyle style1 = workbook.createCellStyle();
			style1.setWrapText(true);
			style1.setAlignment(HorizontalAlignment.LEFT);
			style1.setVerticalAlignment(VerticalAlignment.CENTER);

			style1.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			style1.setFillPattern(FillPatternType.DIAMONDS);

			Font defaultFont1 = workbook.createFont();
			defaultFont1.setBold(true);
			defaultFont1.setFontHeight((short) 300);

			style1.setFont(defaultFont1);

			empLastNameCell.setCellStyle(style1);

			workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
