package com.sample.app;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class App {

	public static void main(String[] args) {

		Javalin javalin = Javalin.create();

		javalin.get("/", (ctx) -> {
			ctx.result("Welcome to Javalin programming!!!!");
		});

		javalin.get("/say-hello", (ctx) -> {

			String name = ctx.queryParamAsClass("name", String.class)
					.check(name1 -> name1 != null && name1.length() > 3, "name length is less than 4 characters").get();

			ctx.result("Hello " + name + ", have a good day!!!!").status(HttpStatus.OK);
		});

		javalin.start(8080);
	}

}