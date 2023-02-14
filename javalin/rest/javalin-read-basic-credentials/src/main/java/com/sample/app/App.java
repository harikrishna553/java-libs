package com.sample.app;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.security.BasicAuthCredentials;

public class App {

	public static void main(String[] args) {

		Javalin javalin = Javalin.create();

		javalin.get("/", (ctx) -> {
			ctx.result("Welcome to Javalin programming!!!!");
		});

		javalin.get("/hello", (ctx) -> {

			BasicAuthCredentials basicAuthcredentials = ctx.basicAuthCredentials();

			if (basicAuthcredentials == null) {
				ctx.result("Credentials missing").status(HttpStatus.BAD_REQUEST);
				return;
			}

			String username = basicAuthcredentials.getUsername();
			String password = basicAuthcredentials.getPassword();

			if (username == null || !"test".equals(username)) {
				ctx.result("INVALID CREDENTIALS").status(HttpStatus.UNAUTHORIZED);
				return;
			}

			if (password == null || !"test".equals(password)) {
				ctx.result("INVALID CREDENTIALS").status(HttpStatus.UNAUTHORIZED);
				return;
			}

			ctx.result("Good morning!!!!").status(HttpStatus.OK);
		});

		javalin.start(8080);
	}

}
