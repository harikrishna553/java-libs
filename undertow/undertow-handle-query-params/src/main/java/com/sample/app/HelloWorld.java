package com.sample.app;

import static io.undertow.Handlers.path;

import com.sample.app.handlers.DemoRequestHandler;

import io.undertow.Undertow;

public class HelloWorld {
	public static void main(final String[] args) {

		final Undertow underTowServer = Undertow.builder()
				.addHttpListener(8080, "0.0.0.0")
				.setHandler(path()
						.addPrefixPath("/demo", new DemoRequestHandler()))
				.build();
		
		underTowServer.start();
	}
}
