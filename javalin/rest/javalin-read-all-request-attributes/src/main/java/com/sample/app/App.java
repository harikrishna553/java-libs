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
			ctx.attribute("name", "Krishna");
			ctx.attribute("age", 34);

			Map<String, Object> attributeMap = ctx.attributeMap();

			Map<String, String> requestAttributes = new HashMap<>();
			for (String key : attributeMap.keySet()) {
				Object val = attributeMap.get(key);

				if (val != null) {
					requestAttributes.put(key, val.toString());
				}
			}

			String json = JsonUtil.getJson(requestAttributes);

			ctx.result(json).status(HttpStatus.OK);
		});

		javalin.start(8080);
	}

}
