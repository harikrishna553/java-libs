package com.sample.app.handlers;

import java.util.HashMap;
import java.util.Map;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class DemoRequestHandler implements HttpHandler {

	@SuppressWarnings("rawtypes")
	private static final Map<String, String> RESOURCES_MSGS_MAP = new HashMap() {
		{
			this.put("home", "Welcome to Undertow.....");
			this.put("hello", "Hello user!!!!!!");
			this.put("greet-me", "Have a good day........");
			this.put("none", "Hello World");
		}
	};

	private final String resource;

	public DemoRequestHandler(String resource) {
		this.resource = resource;
	}

	public void handleRequest(HttpServerExchange exchange) throws Exception {
		exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");

		String msg = RESOURCES_MSGS_MAP.get(resource);

		exchange.getResponseSender().send(msg + "\n");
	}

}
