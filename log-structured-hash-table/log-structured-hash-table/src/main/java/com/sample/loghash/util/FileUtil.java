package com.sample.loghash.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
	public static void createFileIfNotExists(String file) throws IOException {
		Path path = Paths.get(file);

		try {
			// Create the file, including any necessary parent directories
			Files.createDirectories(path.getParent());
			Files.createFile(path);
			//System.out.println("File created successfully: " + path);

		} catch (FileAlreadyExistsException e) {
			System.out.println("File already exists: " + path);
			//e.printStackTrace();

		} catch (IOException e) {
			//e.printStackTrace();
			System.err.println("An error occurred while creating the file: " + e.getMessage());
		}

	}

	public static String readFileContentViaReadingAllBytes(String filePath) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
	}

	public static void write(final String filePath, final String data) {

		if (data == null || data.isEmpty()) {
			//System.out.println("data is null (or) empty");
			return;
		}

		if (filePath == null) {
			//System.out.println("file is null");
			return;
		}

		File file = new File(filePath);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void renameFile(String oldFileName, String newFileName) {
		File oldFile = new File(oldFileName);
		File newFile = new File(newFileName);

		if (oldFile.exists()) {
			boolean success = oldFile.renameTo(newFile);

			if (success) {
				//System.out.println("File renamed successfully.");
			} else {
				System.err.println("Failed to rename the file.");
			}
		} else {
			System.err.println("File does not exist: " + oldFileName);
		}
	}

	public static String getParentDirectory(String filePath) {
		Path path = Paths.get(filePath);
		Path parent = path.getParent();

		if (parent != null) {
			return parent.toString();
		} else {
			return "No parent directory (it might be the root directory)";
		}
	}

	public static String getFileNameWithoutExtension(String filePath) {
		Path path = Paths.get(filePath);
		String fileNameWithExtension = path.getFileName().toString();

		int lastDotIndex = fileNameWithExtension.lastIndexOf('.');

		if (lastDotIndex != -1) {
			return fileNameWithExtension.substring(0, lastDotIndex);
		} else {
			// File has no extension
			return fileNameWithExtension;
		}
	}

	public static String getFileExtension(String filePath) {
		Path path = Paths.get(filePath);
		String fileNameWithExtension = path.getFileName().toString();

		int lastDotIndex = fileNameWithExtension.lastIndexOf('.');

		if (lastDotIndex != -1 && lastDotIndex < fileNameWithExtension.length() - 1) {
			return fileNameWithExtension.substring(lastDotIndex + 1);
		} else {
			// No file extension
			return "";
		}
	}

}
