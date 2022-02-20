package com.sample.app;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;

public class AppenderDemo {

	public static void main(String[] args) {

		ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
				.getLogger(HelloWorld.class);

		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		PatternLayoutEncoder ple = new PatternLayoutEncoder();

		ple.setPattern("%date %level [%thread] %logger{10} [%file:%line] %msg%n");
		ple.setContext(loggerContext);
		ple.start();
		FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
		fileAppender.setFile("/Users/Shared/logback/app.log");
		fileAppender.setEncoder(ple);
		fileAppender.setContext(loggerContext);
		fileAppender.start();

		logger.addAppender(fileAppender);
		logger.setAdditive(false);

		logger.trace("Trace message");
		logger.debug("Debug message");
		logger.info("Info message");
		logger.warn("Warning message");
		logger.error("Error message");

	}
}