package com.sample.app;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

public class HelloWorld {

	private static void sleep(int noOfSeconds) {
		try {
			TimeUnit.SECONDS.sleep(noOfSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void printLogMessages() {
		Logger logger = LoggerFactory.getLogger(HelloWorld.class);
		logger.trace("Trace message");
		logger.debug("Debug message");
		logger.info("Info message");
		logger.warn("Warning message");
		logger.error("Error message");
	}

	public static void main(String[] args) {

		printLogMessages();

		System.out.println("\nSleeping for 2 seconds\n");
		sleep(2);

		ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory
				.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
		rootLogger.setLevel(Level.WARN);

		System.out.println("\nUpdated root logger logging level to WARN\n");
		printLogMessages();
	}
}
