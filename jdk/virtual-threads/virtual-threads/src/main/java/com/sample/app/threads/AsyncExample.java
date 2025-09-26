package com.sample.app.threads;

import java.util.concurrent.CompletableFuture;

public class AsyncExample {
	public static void main(String[] args) {
		// Start two async API calls
		CompletableFuture<String> api1 = CompletableFuture.supplyAsync(() -> fetch("API 1"));
		CompletableFuture<String> api2 = CompletableFuture.supplyAsync(() -> fetch("API 2"));

		// While APIs are running, main thread can do other work
		System.out.println("Main thread is free to do something else...");

		// Combine results of both APIs once they are ready
		CompletableFuture<String> combined = api1.thenCombine(api2, (res1, res2) -> {
			return res1 + " + " + res2;
		});

		// Add further processing (non-blocking)
		CompletableFuture<Void> pipeline = combined.thenApply(result -> "Processed: " + result)
				.thenAccept(finalResult -> System.out.println("Final Output -> " + finalResult)).exceptionally(ex -> {
					System.out.println("Something went wrong: " + ex.getMessage());
					return null;
				});

		// Wait for all tasks to complete before exiting
		pipeline.join();
		System.out.println("Program finished!");
	}

	static String fetch(String apiName) {
		try {
			System.out.println(Thread.currentThread().getName() + " is fetching " + apiName);
			Thread.sleep(500); // Simulate delay
		} catch (InterruptedException ignored) {
		}
		return "Result from " + apiName;
	}
}
