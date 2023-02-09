package com.sample.app;

import io.javalin.Javalin;

public class App {
	public static void main(String[] args) {
		Javalin javalin = Javalin.create();

		javalin.get("/", (ctx) -> {
			ctx.result("Welcome to Javalin programming!!!!");
		});
		
		javalin.get("/hello", (ctx) -> {
			ctx.result("Hello World");
		});

		javalin.start(8080);
	}
}