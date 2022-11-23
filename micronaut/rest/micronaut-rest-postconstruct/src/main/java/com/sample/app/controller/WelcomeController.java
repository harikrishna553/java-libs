package com.sample.app.controller;

import com.sample.app.config.AppConfig;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

@Controller("/welcome")
public class WelcomeController {

	@Inject
	private AppConfig appConfig;
	
	@Get(produces = MediaType.TEXT_PLAIN)
	public String index() {
		return "Welcome to Micronaut app!!!!";
	}
}