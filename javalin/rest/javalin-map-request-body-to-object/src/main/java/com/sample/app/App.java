package com.sample.app;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.sample.app.dto.EmployeeRequestDto;
import com.sample.app.util.JsonUtil;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;

public class App {
	private static AtomicInteger empIdCounter = new AtomicInteger(1);

	public static void main(String[] args) {

		Javalin javalin = Javalin.create();

		javalin.get("/", (ctx) -> {
			ctx.result("Welcome to Javalin programming!!!!");
		});

		javalin.post("/employees", (ctx) -> {
			EmployeeRequestDto employeeRequestDto = ctx.bodyAsClass(EmployeeRequestDto.class);

			Map<String, Object> map = new HashMap<>();
			map.put("id", empIdCounter.getAndIncrement());
			map.put("name", employeeRequestDto.getName());
			map.put("age", employeeRequestDto.getAge());

			String respJson = JsonUtil.getJson(map);

			ctx.res().setHeader("Content-Type", "application/json");
			ctx.result(respJson).status(HttpStatus.CREATED);
		});

		javalin.start(8080);
	}
}
