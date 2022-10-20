package com.sample.app.event.listeners;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.sample.app.event.ConnectionCreateEvent;

import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Singleton;

@Singleton
public class EventListeners {

	private static void sleepRandTime() {
		Random random = new Random();
		int n = random.nextInt(10);
		try {
			TimeUnit.MICROSECONDS.sleep(n * 100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@EventListener
	@Async
	public void onCreateEvent(ConnectionCreateEvent event) {
		sleepRandTime();
		System.out.println(Thread.currentThread().getName() + " received event : '" + event.getMessage() + "'");
	}

}