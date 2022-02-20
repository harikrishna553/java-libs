package com.sample.app;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

public class LoggingSelectionRuleDemo {

	public static void main(String[] args) {
		ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
				.getLogger(LoggingLevelsDemo.class);
		logger.setLevel(Level.INFO);

		logger.trace("Trace message");
		logger.debug("Debug message");
		logger.info("Info message");
		logger.warn("Warning message");
		logger.error("Error message");

	}
}