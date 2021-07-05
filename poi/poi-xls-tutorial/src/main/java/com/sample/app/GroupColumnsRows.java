package com.sample.app;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class GroupColumnsRows {
	public static void main(String args[]) {
		String xlsFileName = "/Users/Shared/poi/xls/abc_org.xls";

		try (Workbook workbook = new HSSFWorkbook(); OutputStream os = new FileOutputStream(xlsFileName)) {

			Sheet sheet = workbook.createSheet("test");

			sheet.groupRow(3, 13);
			sheet.groupColumn(2, 11);

			workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
