package com.sample.app;

import java.io.IOException;

import com.sample.app.util.FileUtil;

import net.coobird.thumbnailator.Thumbnails;

public class ScaleByFactor {
	public static void main(String[] args) throws IOException {
		String resourceFileName = "taj-mahal.png";
		String thumbnailFileName = "/Users/Shared/thumbnails/taj-mahal-hello-world.png";

		Thumbnails.of(FileUtil.resourceFilePath(resourceFileName)).scale(0.1).toFile(thumbnailFileName);
	}

}
