package com.sample.app.event.publishers;

import com.sample.app.event.ConnectionCreateEvent;

import io.micronaut.context.event.ApplicationEventPublisher;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class ConnectionEventPublisher {

	@Inject
	ApplicationEventPublisher<ConnectionCreateEvent> createEventPublisher;

	public void publishCreateEvent(String message) {
		createEventPublisher.publishEvent(new ConnectionCreateEvent(message));
	}

}
