package com.sample.app.controller;

import java.util.StringJoiner;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;

@Controller("/hello")
public class HelloController {

	@Get(value = "/even-numbers", produces = MediaType.TEXT_PLAIN)
	public String evenNumbers(@Nullable @QueryValue("from") Integer from, @Nullable @QueryValue("to") Integer to) {

		final StringJoiner stringJoiner = new StringJoiner(",");

		for (int i = from; i < to; i += 2) {
			stringJoiner.add("" + i);
		}

		return stringJoiner.toString();
	}
}