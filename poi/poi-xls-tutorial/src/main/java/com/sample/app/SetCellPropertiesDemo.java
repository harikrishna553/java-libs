package com.sample.app;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;

public class SetCellPropertiesDemo {
	public static void main(String args[]) {
		String xlsFileName = "/Users/Shared/poi/xls/abc_org.xls";

		try (Workbook workbook = new HSSFWorkbook(); OutputStream os = new FileOutputStream(xlsFileName)) {

			HashMap<String, Object> properties = new HashMap<String, Object>();

			// Set borders
			properties.put(CellUtil.BORDER_TOP, BorderStyle.DASH_DOT);
			properties.put(CellUtil.BORDER_BOTTOM, BorderStyle.DASH_DOT_DOT);
			properties.put(CellUtil.BORDER_LEFT, BorderStyle.HAIR);
			properties.put(CellUtil.BORDER_RIGHT, BorderStyle.MEDIUM);

			// Set border color
			properties.put(CellUtil.TOP_BORDER_COLOR, IndexedColors.BLUE.getIndex());
			properties.put(CellUtil.BOTTOM_BORDER_COLOR, IndexedColors.RED.getIndex());
			properties.put(CellUtil.LEFT_BORDER_COLOR, IndexedColors.GREEN.getIndex());
			properties.put(CellUtil.RIGHT_BORDER_COLOR, IndexedColors.AQUA.getIndex());

			// Set foreground color
			properties.put(CellUtil.FILL_FOREGROUND_COLOR, IndexedColors.AQUA.getIndex());
			properties.put(CellUtil.FILL_PATTERN, FillPatternType.BIG_SPOTS);

			Sheet employeeSheet = workbook.createSheet("employees");
			employeeSheet.setDefaultRowHeightInPoints(30);

			for (int i = 0; i < 10; i++) {
				Row row = employeeSheet.createRow(i);

				Cell cell1 = row.createCell(0);
				cell1.setCellValue("id_" + i);

				Cell cell2 = row.createCell(1);
				cell2.setCellValue("name_" + i);

				Cell cell3 = row.createCell(2);
				cell3.setCellValue("age_" + i);

				CellUtil.setCellStyleProperties(cell1, properties);
				CellUtil.setCellStyleProperties(cell2, properties);
				CellUtil.setCellStyleProperties(cell3, properties);

			}

			workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
