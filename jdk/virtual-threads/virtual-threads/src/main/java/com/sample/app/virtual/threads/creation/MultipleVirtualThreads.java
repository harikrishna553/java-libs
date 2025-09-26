package com.sample.app.virtual.threads.creation;

public class MultipleVirtualThreads {
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = Thread.ofVirtual().start(() -> {
			System.out.println("Task 1 running in " + Thread.currentThread());
		});

		Thread t2 = Thread.ofVirtual().start(() -> {
			System.out.println("Task 2 running in " + Thread.currentThread());
		});

		t1.join();
		t2.join();
	}
}
