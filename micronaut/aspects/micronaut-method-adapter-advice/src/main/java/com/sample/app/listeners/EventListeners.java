package com.sample.app.listeners;

import com.sample.app.event.annotation.MyEventListener;
import com.sample.app.events.ConnectionCloseEvent;
import com.sample.app.events.ConnectionCreateEvent;

import jakarta.inject.Singleton;

@Singleton
public class EventListeners {

	@MyEventListener
	public void onCreateEvent(ConnectionCreateEvent event) {
		System.out.println("create event received : '" + event.getMessage() + "'");
	}

	@MyEventListener
	public void onCloseEvent(ConnectionCloseEvent event) {
		System.out.println("close event received : '" + event.getMessage() + "'");
	}

}