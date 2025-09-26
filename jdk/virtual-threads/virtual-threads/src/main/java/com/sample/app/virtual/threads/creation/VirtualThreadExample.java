package com.sample.app.virtual.threads.creation;

public class VirtualThreadExample {
	public static void main(String[] args) {
		Thread virtualThread = Thread.ofVirtual().start(() -> {
			System.out.println("Running in a virtual thread: " + Thread.currentThread());
		});

		try {
			virtualThread.join(); // wait for the thread to finish
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Finished Execution of Virtual Thread work....");
	}
}
