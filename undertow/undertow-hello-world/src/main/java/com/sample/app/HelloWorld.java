package com.sample.app;

import io.undertow.Undertow;
import io.undertow.util.Headers;

public class HelloWorld {
	public static void main(final String[] args) {

		final Undertow undertowServer = Undertow.builder().addHttpListener(8080, "localhost").setHandler(exchange -> {
			exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
			exchange.getResponseSender().send("Hello World......");
		}).build();

		undertowServer.start();
	}
}
