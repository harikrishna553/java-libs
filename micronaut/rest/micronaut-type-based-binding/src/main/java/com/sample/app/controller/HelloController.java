package com.sample.app.controller;

import java.util.HashMap;
import java.util.Map;

import io.micronaut.http.BasicAuth;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/hello")
public class HelloController {

	@Get(value = "/type-binding-demo", produces = MediaType.APPLICATION_JSON)
	public Map<String, String> demo(final BasicAuth credentials) {

		final Map<String, String> map = new HashMap<>();
		map.put("username", credentials.getUsername());
		map.put("password", credentials.getPassword());

		return map;

	}

}
