package com.sample.app.virtual.threads.creation;

public class ThreadBuilderExceptionHadnler {
	static void handleUserRequest() {
		int a = 10 /0 ;
		System.out.println(a);
	}

	public static void main(String[] args) throws InterruptedException {
		// Create a virtual thread builder with a custom name pattern
		Thread.Builder builder = Thread.ofVirtual()
			    .name("api-thread-", 0)
			    .uncaughtExceptionHandler((t, e) -> {
			        System.out.println("Thread " + t.getName() + " crashed: " + e.getMessage());
			    });

		// Start threads using the builder
		Thread t1 = builder.start(ThreadBuilderExceptionHadnler::handleUserRequest);
		Thread t2 = builder.start(ThreadBuilderExceptionHadnler::handleUserRequest);

		// Wait for both to finish
		t1.join();
		t2.join();
	}
}
