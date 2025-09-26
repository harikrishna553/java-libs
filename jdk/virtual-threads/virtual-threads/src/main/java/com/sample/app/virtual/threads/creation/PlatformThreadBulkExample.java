package com.sample.app.virtual.threads.creation;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class PlatformThreadBulkExample {

	public static void main(String[] args) {
		IntStream.range(0, 1000).forEach(i -> {
			Thread.ofPlatform().start(() -> {
				try {
					TimeUnit.SECONDS.sleep(60);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Fetching data for request " + i + " in " + Thread.currentThread());
			});
		});
	}

}
