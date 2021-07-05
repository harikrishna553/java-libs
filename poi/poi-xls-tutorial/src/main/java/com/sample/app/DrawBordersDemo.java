package com.sample.app;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderExtent;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.PropertyTemplate;

public class DrawBordersDemo {
	public static void main(String args[]) {
		try (Workbook workBook = new HSSFWorkbook();
				OutputStream os = new FileOutputStream("/Users/Shared/poi/xls/abc_org.xls")) {
			PropertyTemplate propertyTemplate = new PropertyTemplate();

			propertyTemplate.drawBorders(new CellRangeAddress(1, 5, 1, 5), BorderStyle.MEDIUM, IndexedColors.GREEN.getIndex(),
					BorderExtent.ALL);
			propertyTemplate.drawBorders(new CellRangeAddress(1, 5, 6, 10), BorderStyle.MEDIUM, IndexedColors.RED.getIndex(),
					BorderExtent.OUTSIDE);
			propertyTemplate.drawBorders(new CellRangeAddress(6, 10, 1, 7), BorderStyle.THIN, IndexedColors.VIOLET.getIndex(),
					BorderExtent.ALL);

			propertyTemplate.drawBorders(new CellRangeAddress(6, 10, 8, 10), BorderStyle.MEDIUM, IndexedColors.BLUE.getIndex(),
					BorderExtent.OUTSIDE);

			Sheet sheet = workBook.createSheet("employees");
			propertyTemplate.applyBorders(sheet);
			workBook.write(os);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}