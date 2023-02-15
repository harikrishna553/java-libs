package com.sample.app;

import io.javalin.Javalin;
import io.javalin.http.HandlerType;
import io.javalin.http.HttpStatus;

public class App {

	public static void main(String[] args) {

		Javalin javalin = Javalin.create();

		javalin.get("/", (ctx) -> {
			ctx.result("Welcome to Javalin programming!!!!");
		});

		javalin.get("/hello", (ctx) -> {
			HandlerType headerType = ctx.method();
			String methodName = headerType.name();
			System.out.println("http method : " + methodName);

			ctx.result("Good morning!!!").status(HttpStatus.OK);
		});

		javalin.start(8080);
	}

}
