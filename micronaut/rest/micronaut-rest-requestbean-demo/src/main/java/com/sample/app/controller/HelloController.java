package com.sample.app.controller;

import java.util.StringJoiner;

import com.sample.app.model.NumbersMeta;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.RequestBean;

@Controller("/hello")
public class HelloController {

	@Get(value = "/even-numbers", produces = MediaType.TEXT_PLAIN)
	public String evenNumbers(@RequestBean NumbersMeta numbersMeta) {

		Integer from = numbersMeta.getFrom();
		Integer to = numbersMeta.getTo();

		if (from == null || from < 0) {
			from = 0;
		}

		if (to == null || to < 0) {
			to = from + 10;
		}

		final StringJoiner stringJoiner = new StringJoiner(",");

		for (int i = from; i < to; i += 2) {
			stringJoiner.add("" + i);
		}

		return stringJoiner.toString();
	}
}
