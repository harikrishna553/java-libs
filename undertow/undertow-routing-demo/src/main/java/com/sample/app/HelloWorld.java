package com.sample.app;

import com.sample.app.handlers.DemoRequestHandler;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;

public class HelloWorld {
	private static HttpHandler APP_ROUTES = new RoutingHandler()
			.get("/", new DemoRequestHandler("home"))
			.get("/hello", new DemoRequestHandler("hello"))
			.get("/greet-user", new DemoRequestHandler("greet-me"))
			.setFallbackHandler(new DemoRequestHandler("none"));

	public static void main(final String[] args) {

		final Undertow undertowServer = Undertow.builder().addHttpListener(8080, "localhost", APP_ROUTES).build();

		undertowServer.start();
	}
}