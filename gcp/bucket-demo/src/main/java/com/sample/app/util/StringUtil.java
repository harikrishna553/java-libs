package com.sample.app.util;

public class StringUtil {

	public static boolean isNullOrBlank(String str) {
		if (str == null) {
			return true;
		}

		return str.isBlank();
	}

	public static void checkNotBlank(String str, String label) {
		if (isNullOrBlank(str)) {
			throw new RuntimeException(label + " is null or empty");
		}
	}

}
