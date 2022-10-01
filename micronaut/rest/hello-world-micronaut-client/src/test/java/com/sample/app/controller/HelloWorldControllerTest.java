package com.sample.app.controller;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class HelloWorldControllerTest {

	@Inject
	EmbeddedServer server;

	@Inject
	@Client("/")
	HttpClient client;

	@Test
	void testHelloWorldResponse() {
		String response = client.toBlocking().retrieve(HttpRequest.GET("/hello"));
		assertEquals("Hello World", response);
	}
}