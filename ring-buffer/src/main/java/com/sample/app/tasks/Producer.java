package com.sample.app.tasks;

import com.sample.app.collections.RingBuffer;
import com.sample.app.util.TimerUtil;

public class Producer implements Runnable {
	private RingBuffer<String> buffer;

	public Producer(RingBuffer<String> buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			boolean ispublished = buffer.publish("Event " + i);
			if(!ispublished) {
				System.out.println("Buffer is full. Producer waiting...");
				TimerUtil.sleepNMilliseconds(100);
				i--;
				continue;
			}
		}
	}
}