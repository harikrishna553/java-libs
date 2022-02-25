package com.sample.app;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;

public class HelloWorld {

	public static void main(String[] args) {

		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

		PatternLayoutEncoder patternLayoutEncoder = new PatternLayoutEncoder();
		patternLayoutEncoder.setPattern("%date %level [%thread] %logger{10} [%file:%line] %msg%n");
		patternLayoutEncoder.setContext(loggerContext);
		patternLayoutEncoder.start();

		FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
		fileAppender.setFile("/Users/Shared/logback/app.log");
		fileAppender.setEncoder(patternLayoutEncoder);
		fileAppender.setContext(loggerContext);
		fileAppender.start();

		Logger logger = (Logger) LoggerFactory.getLogger(HelloWorld.class);
		logger.addAppender(fileAppender);
		logger.setLevel(Level.INFO);
		logger.setAdditive(false);

		logger.trace("Trace message");
		logger.debug("Debug message");
		logger.info("Info message");
		logger.warn("Warning message");
		logger.error("Error message");

	}
}