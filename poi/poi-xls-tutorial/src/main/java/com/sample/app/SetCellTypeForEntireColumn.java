package com.sample.app;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class SetCellTypeForEntireColumn {

	private static List<Integer> empIds = Arrays.asList(2, 3, 5, 7, 11);
	private static List<String> names = Arrays.asList("Krishna", "Ram", "Sailu", "Lahari", "Siva");
	private static List<Boolean> isMaleList = Arrays.asList(true, true, false, false, true);

	public static void main(String args[]) {
		String xlsFileName = "/Users/Shared/poi/xls/abc_org.xls";

		try (Workbook workbook = new HSSFWorkbook(); OutputStream os = new FileOutputStream(xlsFileName)) {
			DataFormat fmt = workbook.createDataFormat();
			

			Sheet employeeSheet = workbook.createSheet("employees");
			
			CellStyle intStyle = workbook.createCellStyle();
			short intFormat = fmt.getFormat("0");
			intStyle.setDataFormat(intFormat);
			employeeSheet.setDefaultColumnStyle(0, intStyle);
			
			
			CellStyle textStyle = workbook.createCellStyle();
			short textFormat = fmt.getFormat("@");
			textStyle.setDataFormat(textFormat);
			employeeSheet.setDefaultColumnStyle(1, textStyle);
			
			employeeSheet.
			
			
			
			//workbook.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
