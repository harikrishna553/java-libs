package com.sample.app.virtual.threads.creation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class QuickTasks {
	private static void sleep(int noOfSeconds) {
		try {
			TimeUnit.SECONDS.sleep(noOfSeconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		List<Thread> threads = new ArrayList<>();

		for (int i = 1; i <= 3; i++) {
			final int k = i;
			Thread t = Thread.ofVirtual().start(() -> {
				System.out.println("Running task " + k + " in " + Thread.currentThread());
				sleep(1);
				System.out.println("Finished task " + k + " in " + Thread.currentThread());
			});

			threads.add(t);
		}

		for (Thread thread : threads) {
			thread.join();
		}
	}
}
