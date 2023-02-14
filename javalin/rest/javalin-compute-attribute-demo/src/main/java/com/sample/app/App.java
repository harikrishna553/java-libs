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

		javalin.get("/attributes-demo", (ctx) -> {

			ctx.attribute("id", 123);
			ctx.attribute("default_name", "no_name");

			Integer id = ctx.attribute("id");
			String name = (String) ctx.attributeOrCompute("name", context -> {
				return context.attribute("default_name");
			});

			Map<String, Object> map = new HashMap<>();

			map.put("id", id);
			map.put("name", name);

			String json = JsonUtil.getJson(map);

			ctx.result(json).status(HttpStatus.OK);
		});

		javalin.start(8080);
	}

}
