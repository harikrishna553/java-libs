package com.sample.app;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class App {

	public static void main(String[] args) {

		Javalin javalin = Javalin.create();

		javalin.get("/", (ctx) -> {
			ctx.result("Welcome to Javalin programming!!!!");
		});

		javalin.get("/register-me/{name}/{age}", (ctx) -> {

			String name = ctx.pathParam("name");
			Integer age = ctx
					.pathParamAsClass("age", Integer.class)
					.check(myAge -> myAge >= 18, "age is less than 18")
					.get();

			ctx.result("Hello " + name + ", you are " + age + " years old").status(HttpStatus.OK);
		});

		javalin.start(8080);
	}

}

