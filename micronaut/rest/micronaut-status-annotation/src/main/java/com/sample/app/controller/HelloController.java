package com.sample.app.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;

@Controller("/hello")
public class HelloController {

	@Post(value = "/demo1", produces = MediaType.APPLICATION_JSON)
	@Status(HttpStatus.CREATED)
	public Map<String, String> demo1() {

		final Map<String, String> data = new HashMap<>();
		data.put("currentTime", LocalDateTime.now().toString());

		return data;
	}

}