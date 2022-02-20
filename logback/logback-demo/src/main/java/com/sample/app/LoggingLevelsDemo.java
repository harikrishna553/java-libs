package com.sample.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingLevelsDemo {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(LoggingLevelsDemo.class);

		logger.trace("Trace message");
		logger.debug("Debug message");
		logger.info("Info message");
		logger.warn("Warning message");
		logger.error("Error message");

	}
}	