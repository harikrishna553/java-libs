package com.sample.app;

import java.io.IOException;
import java.util.List;

import com.sample.app.util.FileUtil;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.util.ThumbnailatorUtils;

public class SetOutputFormat {
	public static void main(String[] args) throws IOException {
		String resourceFileName = "taj-mahal.png";
		String destinationFolder = "/Users/Shared/thumbnails/";

		List<String> supportedOutputFormats = ThumbnailatorUtils.getSupportedOutputFormats();

		System.out.println("Supported file formats");
		int i = 1;
		for (String supportedFormat : supportedOutputFormats) {
			System.out.println(supportedFormat);
			String destinationFilePath = destinationFolder + "out"+i;
			i++;

			try {
				Thumbnails
				.of(FileUtil.resourceFilePath(resourceFileName))
				.size(160, 160)
				.outputFormat(supportedFormat)
				.toFile(destinationFilePath);
			} catch (Exception e) {
				System.err.println("Error occurred while generating thumbanil in " + supportedFormat
						+ " format. Error :  " + e.getMessage());
			}

		}
		
	}
}
