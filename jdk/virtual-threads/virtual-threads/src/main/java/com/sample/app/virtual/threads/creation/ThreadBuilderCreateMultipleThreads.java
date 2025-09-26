package com.sample.app.virtual.threads.creation;

import java.util.concurrent.TimeUnit;

public class ThreadBuilderCreateMultipleThreads {

	public static void main(String[] args) throws InterruptedException {
		Thread.Builder builder = Thread.ofVirtual().name("worker-", 0);

		for (int i = 0; i < 5; i++) {
			builder.start(() -> {
				System.out.println("Work done by: " + Thread.currentThread());
			});
		}
		
		TimeUnit.SECONDS.sleep(1);

	}

}
