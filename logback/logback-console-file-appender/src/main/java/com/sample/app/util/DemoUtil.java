package com.sample.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoUtil {
	private static Logger logger = LoggerFactory.getLogger(DemoUtil.class);

	public static void print() {

		logger.trace("Trace message");
		logger.debug("Debug message");
		logger.info("Info message");
		logger.warn("Warning message");
		logger.error("Error message");
	}
}
