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

		javalin.post("/form-params-demo", (ctx) -> {

			String name = ctx.formParam("name");
			String age = ctx.formParam("age");

			Map<String, Object> receivedData = new HashMap<>();
			receivedData.put("name", name);
			receivedData.put("age", age);

			ctx.result(JsonUtil.marshal(receivedData)).status(HttpStatus.CREATED);
		});

		javalin.start(8080);
	}

}
