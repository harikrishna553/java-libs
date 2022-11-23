package com.sample.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
public class WelcomeControllerTest {
	@Inject
	EmbeddedServer embeddedServer;

	@Inject
	@Client("/")
	HttpClient httpClient;

	@Test
	void testHelloWorldResponse() {
		String response = httpClient.toBlocking().retrieve(HttpRequest.GET("/welcome"));
		assertEquals("Welcome to Micronaut app!!!!", response);
	}
}
