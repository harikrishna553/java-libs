package com.sample.app;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSimpleShape;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class DrawShapes {
	public static void main(String args[]) {
		String xlsFileName = "/Users/Shared/poi/xls/abc_org.xls";

		try (Workbook workbook = new HSSFWorkbook(); OutputStream os = new FileOutputStream(xlsFileName)) {

			Sheet sheet = workbook.createSheet("test");
			sheet.setDefaultColumnWidth(60);

			Row row1 = sheet.createRow(0);
			row1.setHeightInPoints(300);

			Row row2 = sheet.createRow(1);
			row2.setHeightInPoints(300);

			HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();
			HSSFClientAnchor hssfClientAnchor = new HSSFClientAnchor(0, 0, 250, 255, (short) 1, 0, (short) 1, 0);
			HSSFSimpleShape hssfSimpleShape = patriarch.createSimpleShape(hssfClientAnchor);
			hssfSimpleShape.setShapeType(HSSFSimpleShape.OBJECT_TYPE_OVAL);

			HSSFPatriarch patriarch1 = (HSSFPatriarch) sheet.createDrawingPatriarch();
			HSSFClientAnchor hssfClientAnchor1 = new HSSFClientAnchor(50, 50, 250, 150, (short) 1, 1, (short) 1, 1);
			HSSFSimpleShape hssfSimpleShape1 = patriarch1.createSimpleShape(hssfClientAnchor1);
			hssfSimpleShape1.setShapeType(HSSFSimpleShape.OBJECT_TYPE_RECTANGLE);

			workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
