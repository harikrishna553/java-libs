package com.sample.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadXlsSheet {

	public static void main(String args[]) {
		String xlsFileName = "/Users/Shared/poi/xls/test.xlsx";

		try (Workbook workbook = getWorkbook(xlsFileName)) {

			int noOfSheets = workbook.getNumberOfSheets();

			for (int i = 0; i < noOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);

				System.out.println("Sheet name: " + sheet.getSheetName());

				int noOfRows = sheet.getPhysicalNumberOfRows();

				for (int currentRow = 0; currentRow < noOfRows; currentRow++) {
					Row row = sheet.getRow(currentRow);
					int firstCellNumber = row.getFirstCellNum();
					int lastCellNumber = row.getLastCellNum();
					System.out.println();
					for (int currentCellNumber = firstCellNumber; currentCellNumber < lastCellNumber; currentCellNumber++) {
						Cell cell = row.getCell(currentCellNumber);

						CellType cellType = cell.getCellType();

						if (CellType.STRING.equals(cellType)) {
							System.out.print(cell.getStringCellValue() + "\t");
						} else if (CellType.NUMERIC.equals(cellType)) {
							System.out.print(cell.getNumericCellValue() + "\t");
						} else if (CellType.BOOLEAN.equals(cellType)) {
							System.out.print(cell.getBooleanCellValue() + "\t");
						} else if (CellType.FORMULA.equals(cellType)) {
							System.out.print(cell.getCellFormula() + "\t");
						} else if (CellType.ERROR.equals(cellType)) {
							System.out.print(cell.getErrorCellValue() + "\t");
						}

					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Workbook getWorkbook(String filePath)
			throws EncryptedDocumentException, FileNotFoundException, IOException {
		File file = new File(filePath);
		return WorkbookFactory.create(new FileInputStream(file));

	}

}