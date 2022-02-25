package com.sample.app;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.joran.spi.JoranException;

public class HelloWorld {
	private static Logger logger = LoggerFactory.getLogger(HelloWorld.class);

	public static void main(String[] args) throws JoranException, IOException {

		logger.trace("Trace message");
		logger.debug("Debug message");
		logger.info("Info message");
		logger.warn("Warning message");
		logger.error("Error message");
		
		Demo.printLogMessages();

	}
}