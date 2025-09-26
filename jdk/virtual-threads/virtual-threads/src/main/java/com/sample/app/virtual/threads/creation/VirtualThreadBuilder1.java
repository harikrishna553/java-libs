package com.sample.app.virtual.threads.creation;

public class VirtualThreadBuilder1 {
	static void handleUserRequest() {
		try {
			Thread.sleep(2000); // simulate work
			System.out.println("Handled by: " + Thread.currentThread());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		// Create a virtual thread builder with a custom name pattern
		Thread.Builder builder = Thread.ofVirtual().name("user-thread-", 0);

		// Start threads using the builder
		Thread t1 = builder.start(VirtualThreadBuilder1::handleUserRequest);
		Thread t2 = builder.start(VirtualThreadBuilder1::handleUserRequest);

		// Wait for both to finish
		t1.join();
		t2.join();
	}
}
