package com.sample.app.event.listeners;

import com.sample.app.event.ConnectionCreateEvent;

import io.micronaut.context.event.ApplicationEventListener;
import jakarta.inject.Singleton;

@Singleton
public class ConnectionCreateEventListener implements ApplicationEventListener<ConnectionCreateEvent> {

	@Override
	public void onApplicationEvent(ConnectionCreateEvent event) {
		System.out.println("message : '" + event.getMessage() + "'");
	}

}