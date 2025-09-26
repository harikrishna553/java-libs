package com.sample.app.virtual.threads.creation;

public class PlatformThreadCreation {

	public static void main(String[] args) {
		Thread platformThread = Thread.ofPlatform().start(() -> {
			System.out.println("Running in a platform thread: " + Thread.currentThread());
		});

		try {
			platformThread.join(); // wait for the thread to finish
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Finished Execution of Platform Thread work....");
	}

}
