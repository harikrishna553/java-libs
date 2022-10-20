package com.sample.app;

import com.sample.app.event.publishers.ConnectionCreateEventPublisher;

import io.micronaut.context.ApplicationContext;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {
			ConnectionCreateEventPublisher connectionCreateEventPublisher = applicationContext.getBean(ConnectionCreateEventPublisher.class);

			connectionCreateEventPublisher.publishEvent("Connection1 created");
			connectionCreateEventPublisher.publishEvent("Connection2 created");
			connectionCreateEventPublisher.publishEvent("Connection3 created");
		}

	}
}