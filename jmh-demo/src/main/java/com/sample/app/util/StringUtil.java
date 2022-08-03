package com.sample.app.util;

public class StringUtil {
	
	public static String replaceMultipleSpaces(final String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}

		return str.replaceAll("\\s+", " ");
	}

}
