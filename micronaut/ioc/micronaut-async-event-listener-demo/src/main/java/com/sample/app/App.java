package com.sample.app;

import com.sample.app.event.publishers.ConnectionEventPublisher;

import io.micronaut.context.ApplicationContext;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {
			ConnectionEventPublisher connectionCreateEventPublisher = applicationContext
					.getBean(ConnectionEventPublisher.class);

			int counter = 0;

			for (int i = 0; i < 10; i++) {
				connectionCreateEventPublisher.publishCreateEvent("Connection " + ++counter);
			}

		}

	}
}