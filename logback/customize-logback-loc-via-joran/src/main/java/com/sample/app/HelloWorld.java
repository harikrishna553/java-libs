package com.sample.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class HelloWorld {
	private static Logger logger = LoggerFactory.getLogger(HelloWorld.class);

	public static void main(String[] args) throws JoranException, IOException {

		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		loggerContext.reset();

		JoranConfigurator joranConfigurator = new JoranConfigurator();

		try (InputStream inputStream = new FileInputStream("/Users/Shared/logback/logback.xml")) {
			joranConfigurator.setContext(loggerContext);
			joranConfigurator.doConfigure(inputStream);
		}

		logger.trace("Trace message");
		logger.debug("Debug message");
		logger.info("Info message");
		logger.warn("Warning message");
		logger.error("Error message");

	}
}