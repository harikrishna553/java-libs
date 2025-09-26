package com.sample.app.virtual.threads.creation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThreadsWithExecutorService {

	static void handleUserRequest() {
		try {
			Thread.sleep(2000); // simulate some work
			System.out.println("Handled by: " + Thread.currentThread());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		// Create an ExecutorService that runs each task on its own virtual thread
		try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
			executor.submit(VirtualThreadsWithExecutorService::handleUserRequest);
			executor.submit(VirtualThreadsWithExecutorService::handleUserRequest);
		}

		// This line will only run after both tasks finish
		System.out.println("Main ends after all tasks are done");
	}

}
