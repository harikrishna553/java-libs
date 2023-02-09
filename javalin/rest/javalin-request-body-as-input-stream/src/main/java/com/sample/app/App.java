package com.sample.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
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
			InputStream inputStream = ctx.bodyInputStream();
			
			String payload = inputStreamToString(inputStream);

			EmployeeRequestDto employeeRequestDto = JsonUtil.object(EmployeeRequestDto.class, payload);

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

	public static String inputStreamToString(InputStream inputStream) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		try (Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			int c = 0;
			while ((c = reader.read()) != -1) {
				stringBuilder.append((char) c);
			}
		}

		return stringBuilder.toString();
	}
}
