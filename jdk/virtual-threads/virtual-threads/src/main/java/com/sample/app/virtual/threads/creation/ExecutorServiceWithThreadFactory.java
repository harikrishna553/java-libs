package com.sample.app.virtual.threads.creation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ExecutorServiceWithThreadFactory {

	static void handleUserRequest() {
		try {
			Thread.sleep(2000); // Simulate some work
			System.out.println("Handled by: " + Thread.currentThread());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		// Step 1: Create a custom thread factory, Threads named: user-thread-0,
		// user-thread-1, etc.
		ThreadFactory factory = Thread.ofVirtual().name("user-thread-", 0).factory();

		// Step 2: Create an executor service using the custom factory
		try (ExecutorService executor = Executors.newThreadPerTaskExecutor(factory)) {
			// Step 3: Submit tasks for execution
			executor.submit(ExecutorServiceWithThreadFactory::handleUserRequest);
			executor.submit(ExecutorServiceWithThreadFactory::handleUserRequest);
		} // Step 4: Executor shuts down automatically here
	}

}
