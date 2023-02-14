package com.sample.app;

import java.util.Map;

import com.sample.app.util.JsonUtil;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class App {

	public static void main(String[] args) {

		Javalin javalin = Javalin.create();

		javalin.get("/", (ctx) -> {
			ctx.result("Welcome to Javalin programming!!!!");
		});

		javalin.get("/cookies-demo", (ctx) -> {

			Map<String, String> map = ctx.cookieMap();

			String respJson = JsonUtil.getJson(map);

			ctx.res().setHeader("Content-Type", "application/json");
			ctx.result(respJson).status(HttpStatus.CREATED);

		});

		javalin.start(8080);
	}
}
