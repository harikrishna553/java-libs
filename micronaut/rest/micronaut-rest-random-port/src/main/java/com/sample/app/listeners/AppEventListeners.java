package com.sample.app.listeners;

import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class AppEventListeners {

	@Inject
	EmbeddedServer embeddedServer;

	@EventListener
	public void onStartup(ServerStartupEvent event) {
		System.out.println("host : " + embeddedServer.getHost());
		System.out.println("port : " + embeddedServer.getPort());
	}
}
