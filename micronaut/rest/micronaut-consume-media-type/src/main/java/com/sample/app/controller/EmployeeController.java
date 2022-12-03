package com.sample.app.controller;

import com.sample.app.model.Employee;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/employees")
public class EmployeeController {

	@Post
	public HttpResponse<Employee> create1(@Body final Employee employee) {
		return HttpResponse.status(HttpStatus.CREATED).body(employee);
	}

	@Post("/multiple1")
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	public HttpResponse<Employee> create2(@Body final Employee employee) {
		return HttpResponse.status(HttpStatus.CREATED).body(employee);
	}

	@Post(value = "/multiple2", consumes = {MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	public HttpResponse<Employee> create3(@Body final Employee employee) {
		return HttpResponse.status(HttpStatus.CREATED).body(employee);
	}
}

