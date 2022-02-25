package com.sample.app;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.FileAppender;

public class HelloWorld {

	public static void main(String[] args) {
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

		// Print all the log files location
		for (Logger logger : context.getLoggerList()) {
			Iterator<Appender<ILoggingEvent>> appendersIterator = ((ch.qos.logback.classic.Logger) logger)
					.iteratorForAppenders();

			while (appendersIterator.hasNext()) {
				Appender<ILoggingEvent> appender = appendersIterator.next();

				if (appender instanceof FileAppender) {
					FileAppender<?> fileAppender = (FileAppender<?>) appender;

					System.out.println(fileAppender.getFile());
				}
			}

		}

	}
}