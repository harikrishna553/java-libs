package com.sample.app;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class App {

	public static void main(String[] args) {

		Javalin javalin = Javalin.create();

		javalin.get("/", (ctx) -> {
			ctx.result("Welcome to Javalin programming!!!!");
		});

		javalin.get("/hello/{name}", (ctx) -> {
			
			String name = ctx.pathParam("name");
		

			ctx.result("Hello " + name + ", how are you?").status(HttpStatus.OK);
		});

		javalin.start(8080);
	}

}
