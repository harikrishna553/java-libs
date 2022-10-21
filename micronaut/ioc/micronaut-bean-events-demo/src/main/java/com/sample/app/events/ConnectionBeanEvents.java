package com.sample.app.events;

import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import io.micronaut.context.event.BeanInitializedEventListener;
import io.micronaut.context.event.BeanInitializingEvent;
import jakarta.inject.Singleton;

import com.sample.app.model.Connection;

@Singleton
public class ConnectionBeanEvents
		implements BeanInitializedEventListener<Connection>, BeanCreatedEventListener<Connection> {

	@Override
	public Connection onCreated(BeanCreatedEvent<Connection> event) {
		System.out.println("onCreated: set the port to 3456");
		Connection connection = event.getBean();
		connection.setPort(3456);
		return connection;
	}

	@Override
	public Connection onInitialized(BeanInitializingEvent<Connection> event) {
		System.out.println("onInitialized: set the port to 3456");
		
		Connection connection = event.getBean();
		connection.setPort(1234);
		return connection;
	}

}
