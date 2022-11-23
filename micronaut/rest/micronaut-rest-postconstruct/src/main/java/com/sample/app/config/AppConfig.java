package com.sample.app.config;

import io.micronaut.runtime.server.EmbeddedServer;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class AppConfig {

	@Inject
	EmbeddedServer embeddedServer;

	@PostConstruct
	public void postConstructMethod() {
		System.out.println("host : " + embeddedServer.getHost());
		System.out.println("port : " + embeddedServer.getPort());
	}
	
	public void sayHi() {
		System.out.println("Hi!!!!!!!!!!!");
	}
}
