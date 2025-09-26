package com.sample.app.virtual.threads.creation;

import java.util.concurrent.ThreadFactory;

public class VirtualThreadFactoryDemo1 {
	static void handleUserRequest() {
		try {
			Thread.sleep(2000); // simulate work
			System.out.println("Handled by: " + Thread.currentThread());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		// Create a thread factory with a custom name prefix
		ThreadFactory factory = Thread.ofVirtual().name("user-thread-", 0).factory();

		// Create new threads using the factory
		Thread t1 = factory.newThread(VirtualThreadFactoryDemo1::handleUserRequest);
		Thread t2 = factory.newThread(VirtualThreadFactoryDemo1::handleUserRequest);

		// Start the threads
		t1.start();
		t2.start();

		// Wait for them to finish
		t1.join();
		t2.join();
	}
}
