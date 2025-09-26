package com.sample.app.virtual.threads;

public class HelloVirtualThread {
	
	public static void main(String[] args) throws InterruptedException {
		Thread thread = Thread.startVirtualThread(() -> {
			System.out.println("Is current thread daemon : " + Thread.currentThread().isDaemon());
		    System.out.println("Hello from a virtual thread!");
		});

		thread.join();
	}

}
