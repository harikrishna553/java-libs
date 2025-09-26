package com.sample.app.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrencyDemo {
	public static void main(String[] args) {
		// Create a thread pool with 3 worker threads
		ExecutorService executor = Executors.newFixedThreadPool(3);

		Runnable downloadTask = () -> {
			for (int i = 1; i <= 5; i++) {
				System.out.println(Thread.currentThread().getName() + " - Downloading file part " + i);
				sleep(200);
			}
			System.out.println(Thread.currentThread().getName() + " - Download complete");
		};

		Runnable uploadTask = () -> {
			for (int i = 1; i <= 5; i++) {
				System.out.println(Thread.currentThread().getName() + " - Uploading chunk " + i);
				sleep(150);
			}
			System.out.println(Thread.currentThread().getName() + " - Upload complete");
		};

		Runnable dataProcessingTask = () -> {
			for (int i = 1; i <= 5; i++) {
				System.out.println(Thread.currentThread().getName() + " - Processing record " + i);
				sleep(100);
			}
			System.out.println(Thread.currentThread().getName() + " - Processing complete ");
		};

		// Submit tasks to executor
		executor.submit(downloadTask);
		executor.submit(uploadTask);
		executor.submit(dataProcessingTask);

		// Shut down executor gracefully
		executor.shutdown();
		try {
			if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
				executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			executor.shutdownNow();
		}

		System.out.println("All tasks finished!");
	}

	private static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException ignored) {
		}
	}
}
