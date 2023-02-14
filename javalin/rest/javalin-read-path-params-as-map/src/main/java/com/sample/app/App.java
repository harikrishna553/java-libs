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

		javalin.post("/register-me/{name}/{age}", (ctx) -> {

			Map<String, String> pathParamsMap = ctx.pathParamMap();

			String json = JsonUtil.getJson(pathParamsMap);

			ctx.result(json).status(HttpStatus.CREATED);
		});

		javalin.start(8080);
	}

}
