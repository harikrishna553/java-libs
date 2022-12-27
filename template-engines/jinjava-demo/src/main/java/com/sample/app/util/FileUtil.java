package com.sample.app.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class FileUtil {
	public static String resourceAsString(String resourceName) throws IOException {
		ClassLoader classLoader = FileUtil.class.getClassLoader();
		URL url = classLoader.getResource(resourceName);
		if (url == null) {
			return null;
		}

		URLConnection urlConnection = url.openConnection();

		urlConnection.setUseCaches(false);

		try (InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream())) {
			char[] buffer = new char[1048];
			StringBuilder builder = new StringBuilder();

			int count = -1;
			while ((count = inputStreamReader.read(buffer, 0, buffer.length)) != -1) {
				builder.append(buffer, 0, count);
			}

			return builder.toString();
		}

	}
}
