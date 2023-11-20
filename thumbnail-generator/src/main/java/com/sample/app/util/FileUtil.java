package com.sample.app.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public static String resourceFilePath(String resourceFile) {
		ClassLoader classLoader = FileUtil.class.getClassLoader();
		return resourceFilePath(classLoader, resourceFile);
	}

	private static String resourceFilePath(ClassLoader classLoader, String resourceFile) {
		URL url = classLoader.getResource(resourceFile);
		return url.getPath();
	}

	public static List<String> resourceFilePaths(List<String> resourceFileNames) {
		ClassLoader classLoader = FileUtil.class.getClassLoader();
		List<String> resourcePaths = new ArrayList<>();

		for (String resourceFileName : resourceFileNames) {
			resourcePaths.add(resourceFilePath(classLoader, resourceFileName));
		}

		return resourcePaths;
	}

}