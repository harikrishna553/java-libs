package com.sample.app.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.CookieValue;
import io.micronaut.http.annotation.Get;

@Controller("/hello")
public class HelloController {

	@Get(value = "/cookies", produces = MediaType.TEXT_PLAIN)
	public String printCookies(@CookieValue(value = "myCookie", defaultValue = "no cookie found") String cookie) {
		return cookie;
	}
}