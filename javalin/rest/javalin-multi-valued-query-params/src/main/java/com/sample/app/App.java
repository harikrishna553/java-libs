package com.sample.app;

import java.util.List;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class App {

	public static void main(String[] args) {

		Javalin javalin = Javalin.create();

		javalin.get("/", (ctx) -> {
			ctx.result("Welcome to Javalin programming!!!!");
		});

		javalin.get("/say-hello", (ctx) -> {

			List<String> hobbies = ctx.queryParams("hobbies");
			String name = ctx.queryParam("name");

			ctx.result("Hello " + name + ", your hobbies are : " + hobbies.toString()).status(HttpStatus.OK);
		});

		javalin.start(8080);
	}

}