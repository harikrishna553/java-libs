package com.sample.app;

import java.util.HashMap;
import java.util.List;
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
			List<String> myHobbies = ctx.formParams("hobbies");

			Map<String, Object> receivedData = new HashMap<>();
			receivedData.put("hobbies", myHobbies);

			ctx.result(JsonUtil.marshal(receivedData)).status(HttpStatus.CREATED);
		});

		javalin.start(8080);
	}

}
