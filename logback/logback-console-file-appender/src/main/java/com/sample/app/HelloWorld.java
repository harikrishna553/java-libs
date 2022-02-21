package com.sample.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sample.app.util.DemoUtil;

public class HelloWorld {
	private static Logger logger = LoggerFactory.getLogger(HelloWorld.class);

	public static void main(String[] args) {

		logger.trace("Trace message");
		logger.debug("Debug message");
		logger.info("Info message");
		logger.warn("Warning message");
		logger.error("Error message");
		
		DemoUtil.print();

	}
}