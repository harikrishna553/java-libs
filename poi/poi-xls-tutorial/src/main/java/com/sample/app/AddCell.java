package com.sample.app;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class AddCell {

	public static void main(String args[]) {
		String xlsFileName = "/Users/Shared/poi/xls/abc_org.xls";

		try (Workbook workbook = new HSSFWorkbook(); OutputStream os = new FileOutputStream(xlsFileName)) {
			CellStyle style = workbook.createCellStyle();
			style.setWrapText(true);

			Sheet employeeSheet = workbook.createSheet("employees");

			// Add colunmn headers
			Row row1 = employeeSheet.createRow(0);

			Cell empIdHeader = row1.createCell(0);
			empIdHeader.setCellValue("id");
			empIdHeader.setCellStyle(style);

			Cell empNameHeader = row1.createCell(1);
			empNameHeader.setCellValue("name");
			empNameHeader.setCellStyle(style);

			// Add employee row
			Row row2 = employeeSheet.createRow(1);

			Cell empIdCell = row2.createCell(0);
			empIdCell.setCellValue("1");
			empIdCell.setCellStyle(style);

			Cell empNameCell = row2.createCell(1);
			empNameCell.setCellValue("Krishna");
			empNameCell.setCellStyle(style);

			workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
