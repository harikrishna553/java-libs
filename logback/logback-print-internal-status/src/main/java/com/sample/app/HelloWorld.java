package com.sample.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class HelloWorld {

	public static void main(String[] args) {

		Logger logger = LoggerFactory.getLogger(HelloWorld.class);
		logger.trace("Trace message");
		logger.debug("Debug message");
		logger.info("Info message");
		logger.warn("Warning message");
		logger.error("Error message");

		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		StatusPrinter.print(loggerContext);

	}
}