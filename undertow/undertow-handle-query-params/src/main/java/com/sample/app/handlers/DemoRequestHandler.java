package com.sample.app.handlers;

import java.util.Deque;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

public class DemoRequestHandler implements HttpHandler {

	public void handleRequest(HttpServerExchange exchange) throws Exception {
		exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");

		final Deque<String> value = exchange.getQueryParameters().get("msgType");
		if(value == null) {
			exchange.getResponseSender().send("Hello World!!!!!!");
			return;
		}
		
		final String msgType = value.getFirst();

		if (msgType == null) {
			exchange.getResponseSender().send("Hello World!!!!!!");
			return;
		}

		if ("welcomeMsg".equals(msgType)) {
			exchange.getResponseSender().send("Welcome User, Have a good day......");
			return;
		}

		exchange.getResponseSender().send("Invalid msgType");

	}

}
