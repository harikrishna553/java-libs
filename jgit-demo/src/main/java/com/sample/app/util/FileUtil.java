package com.sample.app.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class FileUtil {
	public static void write(final String filePath, final String data) {
		write(new File(filePath), data);
	}

	public static void write(final File file, final String data) {
		if (data == null || data.isEmpty()) {
			System.out.println("data is null (or) empty");
			return;
		}

		if (file == null) {
			System.out.println("file is null");
			return;
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
