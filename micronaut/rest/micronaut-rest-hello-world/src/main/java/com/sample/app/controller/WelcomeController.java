package com.sample.app.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/welcome")
public class WelcomeController {

	@Get(produces = MediaType.TEXT_PLAIN)
	public String index() {
		return "Welcome to Micronaut app!!!!";
	}
}