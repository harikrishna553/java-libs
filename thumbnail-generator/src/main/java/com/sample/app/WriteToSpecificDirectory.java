package com.sample.app;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.sample.app.util.FileUtil;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

public class WriteToSpecificDirectory {
	
	public static void main(String[] args) throws IOException {
		File destinationDir = new File("/Users/Shared/thumbnails/");
		List<String> resourceFilePaths = FileUtil.resourceFilePaths(Arrays.asList("guava.jpeg", "mango.jpeg"));

		Thumbnails.of(resourceFilePaths.toArray(new String[2]))
		        .size(200, 200)
		        .toFiles(destinationDir, Rename.PREFIX_DOT_THUMBNAIL);
	}

}
