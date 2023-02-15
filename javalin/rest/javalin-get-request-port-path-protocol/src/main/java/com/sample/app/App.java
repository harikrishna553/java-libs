package com.sample.app;

import java.util.HashMap;
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

		javalin.get("/request-details", (ctx) -> {
			String requestPath = ctx.path();
			int port = ctx.port();
			String protocol = ctx.protocol();

			Map<String, Object> map = new HashMap<>();
			map.put("path", requestPath);
			map.put("port", port);
			map.put("protoco", protocol);

			ctx.result(JsonUtil.getJson(map)).status(HttpStatus.OK);
		});

		javalin.start(8080);
	}

}
