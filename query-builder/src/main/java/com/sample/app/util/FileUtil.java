package com.sample.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {

	public static final String readFromClassPath(String fileName) throws Exception {
		try (InputStream is = FileUtil.class.getClassLoader().getResourceAsStream(fileName);
				BufferedReader br = new BufferedReader(new InputStreamReader(is));) {

			String str = null;
			StringBuilder builder = new StringBuilder();

			while ((str = br.readLine()) != null) {
				builder.append(str);
			}

			return builder.toString();
		} catch (Exception e) {
			throw e;
		}
	}

}
