package com.sample.app.virtual.threads.creation;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class VirtualThreadBulkExample {
	public static void main(String[] args) throws InterruptedException {
		IntStream.range(0, 1000).forEach(i -> {
			Thread.ofVirtual().start(() -> {
				try {
					TimeUnit.SECONDS.sleep(60);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Fetching data for request " + i + " in " + Thread.currentThread());
			});
		});

		TimeUnit.SECONDS.sleep(120);
	}

}
