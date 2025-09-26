package com.sample.app.threads;

//MaxThreadsCheck.java
public class MaxThreadsCheck {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java -Xmx1G MaxThreadsCheck <noOfThreadsToTest>");
			return;
		}

		int noOfThreadsToTest;
		try {
			noOfThreadsToTest = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.out.println("Please provide a valid integer for number of threads.");
			return;
		}

		System.out.println("Starting thread test with " + noOfThreadsToTest + " threads...");

		for (int i = 1; i <= noOfThreadsToTest; i++) {
			final int threadNumber = i;

			try {
				Thread t = new Thread(() -> {
					System.out.println("Thread " + threadNumber + " started");
					try {
						Thread.sleep(10_000); // Sleep for 10 seconds
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						System.out.println("Thread " + threadNumber + " interrupted");
					}
					System.out.println("Thread " + threadNumber + " finished");
				});

				t.start();

			} catch (OutOfMemoryError | Exception e) {
				System.out.println("Failed to create thread " + threadNumber + ": " + e.getMessage());
				break;
			}
		}

		System.out.println("Thread creation loop finished. Threads will continue to run for 30 seconds.");
	}
}
