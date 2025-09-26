package com.sample.app.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RequestHandler implements Runnable {
	private final String requestName;

	public RequestHandler(String requestName) {
		this.requestName = requestName;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " started processing " + requestName);

		try {
			// Simulate database call (blocking I/O)
			Thread.sleep(2000); // pretend DB took 2 seconds
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		System.out.println(Thread.currentThread().getName() + " finished processing " + requestName);
	}

	public static void main(String[] args) {
		// Thread-per-request demo
		System.out.println("=== Thread-per-Request Demo (Simple) ===");
		for (int i = 1; i <= 5; i++) {
			Thread thread = new Thread(new RequestHandler("Request-" + i));
			thread.start();
		}

		// Using a thread pool (like real servers do)
		System.out.println("\n=== Thread Pool Demo (Realistic Server) ===");
		ExecutorService threadPool = Executors.newFixedThreadPool(3); // only 3 worker threads
		for (int i = 1; i <= 10; i++) {
			threadPool.submit(new RequestHandler("Pooled-Request-" + i));
		}

		threadPool.shutdown(); // allow tasks to finish
	}
}
