package com.sample.app;

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
			Map<String, List<String>> formParamsMap = ctx.formParamMap();

			ctx.result(JsonUtil.marshal(formParamsMap)).status(HttpStatus.CREATED);
		});

		javalin.start(8080);
	}

}
