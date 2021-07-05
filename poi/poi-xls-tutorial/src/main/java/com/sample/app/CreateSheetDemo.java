package com.sample.app;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

public class CreateSheetDemo {

	public static void main(String args[]) {
		String xlsFileName = "/Users/Shared/poi/xls/abc_org.xls";

		try (Workbook wb = new HSSFWorkbook(); OutputStream os = new FileOutputStream(xlsFileName)) {
			wb.createSheet("employees");
			wb.createSheet("products");
			wb.createSheet("sales");

			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
