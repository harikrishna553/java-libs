package com.sample.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {

	public static void main(String[] args) {

		System.setProperty("logback.configurationFile", "/Users/Shared/logback/logback.xml");

		Logger logger = LoggerFactory.getLogger(HelloWorld.class);
		logger.trace("Trace message");
		logger.debug("Debug message");
		logger.info("Info message");
		logger.warn("Warning message");
		logger.error("Error message");

	}
}