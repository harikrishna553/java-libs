package com.sample.app.controller;

import java.util.HashMap;
import java.util.Map;

import io.micronaut.http.HttpParameters;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/hello")
public class HelloController {

	@Get(value = "/demo1", produces = MediaType.APPLICATION_JSON)
	public HttpResponse<Map<String, String>> demo1(final HttpRequest<?> httpRequest) {

		final HttpParameters httpParameters = httpRequest.getParameters();
		final String city = httpParameters.get("city");
		final String country = httpParameters.get("country");

		final Map<String, String> data = new HashMap<>();
		data.put("city", city);
		data.put("country", country);

		return HttpResponse.ok(data);

	}

}