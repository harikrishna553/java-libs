package com.sample.app;

import com.sample.app.collections.RingBuffer;
import com.sample.app.tasks.Consumer;
import com.sample.app.tasks.Producer;

public class App {
	public static void main(String[] args) {
		RingBuffer<String> buffer = new RingBuffer<>(5);

		// Create and start producer and consumer threads
		Thread producerThread = new Thread(new Producer(buffer));
		Thread consumerThread = new Thread(new Consumer(buffer));

		producerThread.start();
		consumerThread.start();

		try {
			producerThread.join();
			consumerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
