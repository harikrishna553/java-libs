package com.sample.app.event.listeners;

import com.sample.app.event.ConnectionCloseEvent;
import com.sample.app.event.ConnectionCreateEvent;

import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;

@Singleton
public class EventListeners {

	@EventListener
	public void onCreateEvent(ConnectionCreateEvent event) {
		System.out.println("create event received : '" + event.getMessage() + "'");
	}
	
	@EventListener
	public void onCloseEvent(ConnectionCloseEvent event) {
		System.out.println("close event received : '" + event.getMessage() + "'");
	}

}