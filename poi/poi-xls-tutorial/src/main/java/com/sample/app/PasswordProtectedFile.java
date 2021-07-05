package com.sample.app;

import java.io.FileOutputStream;

import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class PasswordProtectedFile {
	public static void main(final String... args) throws Exception {

		String xlsFilePath = "/Users/Shared/poi/xls/abc_org.xls";
		String secret = "secret123";

		try (FileOutputStream fos = new FileOutputStream(xlsFilePath); HSSFWorkbook workbook = new HSSFWorkbook()) {

			// If you comment below line, file always opened in read-only mode
			Biff8EncryptionKey.setCurrentUserPassword(secret);

			Sheet employeeSheet = workbook.createSheet("employees");

			Row row = employeeSheet.createRow(0);
			Cell cell = row.createCell(0);
			cell.setCellValue("PROTECTED");

			workbook.writeProtectWorkbook(secret, "");
			workbook.write(fos);
		}

	}
}
