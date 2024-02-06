package com.sample.app.ratelmiter.util;

public class NumberUtil {

	public static void checkForPositiveNumber(Integer val, String message) {
		if (val == null || val <= 0) {
			throw new IllegalArgumentException(message);
		}
	}
}
