package com.sample.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {

	public static void main(String args[]) {
		Logger logger = LoggerFactory.getLogger(HelloWorld.class);

		logger.info("Welcome to SLF4J logging");
	}

}
