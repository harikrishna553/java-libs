package com.sample.app;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class FooterDemo {
	public static void main(String args[]) {
		String xlsFileName = "/Users/Shared/poi/xls/abc_org.xls";

		try (Workbook workbook = new HSSFWorkbook(); OutputStream os = new FileOutputStream(xlsFileName)) {

			Sheet employeeSheet = workbook.createSheet("employees");

			Header header = employeeSheet.getHeader();
			header.setCenter("Employees information");
			header.setLeft("ABC Corporation");
			header.setRight("click here");

			Footer footer = employeeSheet.getFooter();
			footer.setCenter("This document belongs to abc_org");
			footer.setLeft("1");
			footer.setRight("click here");

			Row row = employeeSheet.createRow(1);
			row.setHeight((short) 500);

			Cell empNameCell = row.createCell(2);
			empNameCell.setCellValue("Krishna");

			workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
