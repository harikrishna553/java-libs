package com.sample.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogErrorDemo {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogErrorDemo.class);

	public static int div(int a, int b) {
		return a / b;
	}

	public static void main(String args[]) {
		int a = 10, b = 0;
		try {
			int e = a / b;
		} catch (Exception e) {
			LOGGER.error("Error Occuured while performing division operaiton a: {}, b: {}", a, b, e);
		}

	}
}
