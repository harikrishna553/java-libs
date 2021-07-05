package com.sample.app;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class MergeCells {
	public static void main(String args[]) {
		String xlsFileName = "/Users/Shared/poi/xls/abc_org.xls";

		try (Workbook workbook = new HSSFWorkbook(); OutputStream os = new FileOutputStream(xlsFileName)) {

			CellStyle style = workbook.createCellStyle();
			style.setWrapText(true);
			style.setAlignment(HorizontalAlignment.LEFT);
			style.setVerticalAlignment(VerticalAlignment.CENTER);

			Sheet employeeSheet = workbook.createSheet("employees");
			employeeSheet.setColumnWidth(2, 5000);
			employeeSheet.setColumnWidth(3, 5000);

			Row row = employeeSheet.createRow(1);
			row.setHeight((short) 500);

			Cell empDescriptionCell = row.createCell(2);
			empDescriptionCell.setCellValue("I am a blogger, traveller.....");
			empDescriptionCell.setCellStyle(style);

			int firstRow = 1;
			int lastRow = 1;
			int firstCol = 2;
			int lastCol = 4;
			CellRangeAddress CellRangeAddress = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
			employeeSheet.addMergedRegion(CellRangeAddress);

			workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

