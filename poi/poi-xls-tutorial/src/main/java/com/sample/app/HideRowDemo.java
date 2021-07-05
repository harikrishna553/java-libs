package com.sample.app;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class HideRowDemo {
	public static void main(String args[]) {
		String xlsFileName = "/Users/Shared/poi/xls/abc_org.xls";

		try (Workbook workbook = new HSSFWorkbook(); OutputStream os = new FileOutputStream(xlsFileName)) {

			Sheet employeeSheet = workbook.createSheet("employees");

			Row row = employeeSheet.createRow(1);
			row.setHeight((short) 500);

			Cell empIdCell = row.createCell(0);
			empIdCell.setCellValue("1");

			Cell empNameCell = row.createCell(1);
			empNameCell.setCellValue("Krishna");

			Cell empAgeCell = row.createCell(2);
			empAgeCell.setCellValue("32");

			row.setHeight((short) 0);

			workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
