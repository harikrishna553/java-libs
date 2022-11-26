package com.sample.app.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.micronaut.core.io.IOUtils;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

@Controller("/hello")
public class HelloController {

	@Post(value = "/read", processes = MediaType.TEXT_PLAIN)
	@ExecuteOn(TaskExecutors.IO)
	String read(@Body InputStream inputStream) throws IOException { 
		return IOUtils.readText(new BufferedReader(new InputStreamReader(inputStream)));
	}
}