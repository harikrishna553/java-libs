package com.sample.app;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class App {

	public static void main(String[] args) {

		Javalin javalin = Javalin.create();

		javalin.get("/", (ctx) -> {
			ctx.result("Welcome to Javalin programming!!!!");
		});

		javalin.post("/echo-file-content", (ctx) -> {

			byte[] requestPayoad = ctx.bodyAsBytes();

			String str = new String(requestPayoad);

			ctx.result(str).status(HttpStatus.CREATED);
		});

		javalin.start(8080);
	}
}