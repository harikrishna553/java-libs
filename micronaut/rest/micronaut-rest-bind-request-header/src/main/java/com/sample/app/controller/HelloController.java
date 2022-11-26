package com.sample.app.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;

@Controller("/hello")
public class HelloController {

	@Get(value = "/headers", produces = MediaType.TEXT_PLAIN)
	public String printHeaders(@Header("Content-Type") String contentType, @Header("secret-key") String secretKey) {

		final StringBuilder builder = new StringBuilder();
		builder.append("contentType").append("=").append(contentType);
		builder.append("\n");
		builder.append("secretKey").append("=").append(secretKey);

		return builder.toString();
	}
}