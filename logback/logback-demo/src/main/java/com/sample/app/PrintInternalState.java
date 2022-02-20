package com.sample.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class PrintInternalState {

	public static void main(String[] args) {

		Logger logger = LoggerFactory.getLogger(PrintInternalState.class);
		logger.debug("Hello world....");

		// print internal state
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		StatusPrinter.print(loggerContext);
	}
}

