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

		javalin.get("/host-details", (ctx) -> {

			String host = ctx.host();
			String ipAddress = ctx.ip();

			Map<String, String> map = new HashMap<>();
			map.put("host", host);
			map.put("ipAddress", ipAddress);

			String respJson = JsonUtil.getJson(map);

			ctx.res().setHeader("Content-Type", "application/json");
			ctx.result(respJson).status(HttpStatus.OK);

		});

		javalin.start(8080);
	}
}
