package com.sample.app;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.resource;

import io.undertow.Undertow;
import io.undertow.server.handlers.resource.ClassPathResourceManager;

public class HelloWorld {
	public static void main(final String[] args) {

		Undertow server = Undertow.builder().addHttpListener(8080, "localhost")
				.setHandler(path()
						.addPrefixPath("/",
								resource(new ClassPathResourceManager(HelloWorld.class.getClassLoader()))
										.addWelcomeFiles("html/index.html"))
						.addPrefixPath("/welcome",
								resource(new ClassPathResourceManager(HelloWorld.class.getClassLoader()))
										.addWelcomeFiles("html/welcome.html")))

				.build();

		server.start();
	}
}
