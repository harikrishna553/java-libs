package com.sample.app.collections;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RingBuffer<T> {
	private int bufferSize;
	private Object[] buffer;
	private int writePointer = 0;
	private int readPointer = 0;
	private final Lock LOCK = new ReentrantLock();

	public RingBuffer(int capacity) {
		this.bufferSize = capacity;
		this.buffer = new Object[capacity];
	}

	public boolean publish(T item) {
		LOCK.lock();
		try {
			// Check if the buffer is full
			if ((writePointer + 1) % bufferSize == readPointer) {
				return false;
			}

			// Add item to the buffer
			buffer[writePointer] = item;
			writePointer = (writePointer + 1) % bufferSize;

			System.out.println("Published: " + item);
			return true;

		} finally {
			LOCK.unlock();
		}
	}

	public T consume() {
		LOCK.lock();
		try {
			// Check if the buffer is empty
			if (readPointer == writePointer) {
				return null;
			}

			// Retrieve item from the buffer
			T item = (T) buffer[readPointer];
			readPointer = (readPointer + 1) % bufferSize;
			return item;

		} finally {
			LOCK.unlock();
		}
	}
}