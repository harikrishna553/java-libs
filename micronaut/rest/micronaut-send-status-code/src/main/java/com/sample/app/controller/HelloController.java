package com.sample.app.controller;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/api")
public class HelloController {

	@Get("/health")
	public HttpStatus ping() {
		return HttpStatus.OK;
	}

}