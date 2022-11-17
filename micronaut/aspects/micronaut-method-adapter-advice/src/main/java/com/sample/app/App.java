package com.sample.app;

import com.sample.app.event.publishers.ConnectionEventPublisher;

import io.micronaut.context.ApplicationContext;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {
			ConnectionEventPublisher connectionCreateEventPublisher = applicationContext
					.getBean(ConnectionEventPublisher.class);

			connectionCreateEventPublisher.publishCreateEvent("Connection1 created");
			connectionCreateEventPublisher.publishCloseEvent("Connection1 closed");
		}
	}
}
