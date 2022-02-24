package com.sample.app;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class MDCDemo {
	private static Logger logger = LoggerFactory.getLogger(MDCDemo.class);

	private static void sleep(int noOfSeconds) {
		try {
			TimeUnit.SECONDS.sleep(noOfSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	static class MyTask implements Runnable {
		private static AtomicInteger counter = new AtomicInteger(1);

		@Override
		public void run() {
			MDC.put("transactionId", "MyTask-" + counter.getAndIncrement());
			logger.info("Started processing the request");
			sleep(1);
			logger.info("Got user details");
			sleep(1);
			logger.info("order placed");
			sleep(1);
			logger.info("Sent notification to the user");

		}

	}

	public static void main(String args[]) {

		ExecutorService executor = Executors.newFixedThreadPool(3);

		for (int i = 0; i < 5; i++) {
			executor.submit(new MyTask());
		}

		executor.shutdown();

	}

}
