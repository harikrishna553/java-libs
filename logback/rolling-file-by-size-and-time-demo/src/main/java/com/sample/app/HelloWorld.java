package com.sample.app;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {
	private static Logger logger = LoggerFactory.getLogger(HelloWorld.class);

	private static void sleep(int noOfSeconds) {
		try {
			TimeUnit.SECONDS.sleep(noOfSeconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		while (true) {
			for (int i = 0; i < 10000; i++) {
				logger.trace("Trace message");
				logger.debug("Debug message");
				logger.info("Info message");
				logger.warn("Warning message");
				logger.error("Error message");
			}

			sleep(1);

		}

	}
}