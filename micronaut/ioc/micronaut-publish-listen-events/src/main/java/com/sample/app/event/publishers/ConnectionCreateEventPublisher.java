package com.sample.app.event.publishers;

import com.sample.app.event.ConnectionCreateEvent;

import io.micronaut.context.event.ApplicationEventPublisher;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class ConnectionCreateEventPublisher {
	
	@Inject
	ApplicationEventPublisher<ConnectionCreateEvent> eventPublisher;

	public void publishEvent(String message) {
		eventPublisher.publishEvent(new ConnectionCreateEvent(message));
	}
}
