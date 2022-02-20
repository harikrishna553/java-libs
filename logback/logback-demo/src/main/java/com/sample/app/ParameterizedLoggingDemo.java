package com.sample.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterizedLoggingDemo {

	public static void main(String[] args) {

		Logger logger = LoggerFactory.getLogger(HelloWorld.class);
		logger.info("App name: {}, version: {}", "chat server", 1.23);

	}
}
