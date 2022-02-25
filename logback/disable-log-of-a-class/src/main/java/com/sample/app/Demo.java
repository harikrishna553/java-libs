package com.sample.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Demo {
	
	private static Logger logger = LoggerFactory.getLogger(Demo.class);


	public static void printLogMessages() {
		logger.trace("Trace message");
		logger.debug("Debug message");
		logger.info("Info message");
		logger.warn("Warning message");
		logger.error("Error message");
	}
}
