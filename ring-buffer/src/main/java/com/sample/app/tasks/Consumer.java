package com.sample.app.tasks;

import com.sample.app.collections.*;
import com.sample.app.util.TimerUtil;

public class Consumer implements Runnable {
	private RingBuffer<String> buffer;

	public Consumer(RingBuffer<String> buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			String itemRead = buffer.consume();
			if (itemRead == null) {
				TimerUtil.sleepNMilliseconds(100);
				i--;
				System.out.println("Buffer is empty. Consumer waiting...");
				continue;
			}
			System.out.println("Consumed: " + itemRead);
		}
	}
}