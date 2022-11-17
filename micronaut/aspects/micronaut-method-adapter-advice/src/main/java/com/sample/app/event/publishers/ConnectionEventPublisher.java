package com.sample.app.event.publishers;

import com.sample.app.events.ConnectionCloseEvent;
import com.sample.app.events.ConnectionCreateEvent;

import io.micronaut.context.event.ApplicationEventPublisher;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class ConnectionEventPublisher {

	@Inject
	ApplicationEventPublisher<ConnectionCreateEvent> createEventPublisher;

	@Inject
	ApplicationEventPublisher<ConnectionCloseEvent> closeEventPublisher;

	public void publishCreateEvent(String message) {
		createEventPublisher.publishEvent(new ConnectionCreateEvent(message));
	}

	public void publishCloseEvent(String message) {
		closeEventPublisher.publishEvent(new ConnectionCloseEvent(message));
	}
}
