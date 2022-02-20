package com.sample.app;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;

public class AppenderAdditivity {

	private static Logger getLogger(String name) {
		Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(name);

		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		PatternLayoutEncoder ple = new PatternLayoutEncoder();

		ple.setPattern("%date %level [%thread] %logger{10} [%file:%line] %msg%n");
		ple.setContext(loggerContext);
		ple.start();
		FileAppender<ILoggingEvent> fileAppender = new FileAppender<ILoggingEvent>();
		fileAppender.setFile("/Users/Shared/logback/" + name + ".log");
		fileAppender.setEncoder(ple);
		fileAppender.setContext(loggerContext);
		fileAppender.start();

		logger.addAppender(fileAppender);
		
		// Comment below lin if you want to enable appender additivity
		logger.setAdditive(false);

		return logger;
	}

	public static void main(String[] args) {

		Logger loggerA = getLogger("A");
		Logger loggerADotB = getLogger("A.B");
		Logger loggerADotBDotC = getLogger("A.B.C");

		loggerADotBDotC.info("Hello world....");
		
	}
}