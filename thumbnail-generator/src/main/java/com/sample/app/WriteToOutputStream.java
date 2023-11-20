package com.sample.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import com.sample.app.util.FileUtil;

import net.coobird.thumbnailator.Thumbnails;

public class WriteToOutputStream {
	public static void main(String[] args) throws IOException {
		String resourceFileName = "taj-mahal.png";

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Thumbnails.of(FileUtil.resourceFilePath(resourceFileName)).size(160, 160).toOutputStream(outputStream);

		String output = Base64.getEncoder().encodeToString(outputStream.toByteArray());
		System.out.println(output);
	}
}
