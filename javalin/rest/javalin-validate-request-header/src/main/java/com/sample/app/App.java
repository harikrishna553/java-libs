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

		javalin.get("/headers-demo", (ctx) -> {

			Integer expireAfter = ctx.headerAsClass("expire_after_in_minutes", Integer.class)
					.check(time -> time <= 10 && time > 5, "Expiry time should be between 5 and 10 inclusive")
					.getOrDefault(8);

			Map<String, Object> map = new HashMap<>();
			map.put("expire_after_in_minutes", expireAfter);

			String respJson = JsonUtil.getJson(map);

			ctx.res().setHeader("Content-Type", "application/json");
			ctx.result(respJson).status(HttpStatus.OK);

		});

		javalin.start(8080);
	}
}
