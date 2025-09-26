package com.example.hellotelemetry.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

	private final RestTemplate restTemplate;

	public HelloController(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
	}

	@GetMapping("/hello")
	public String hello() {
		// A simple outbound call to create a client span for demo
		String downstream = restTemplate.getForObject("https://httpbin.org/get", String.class);
		int len = downstream == null ? 0 : downstream.length();
		return "Hello — downstream returned " + len + " bytes";
	}
}
