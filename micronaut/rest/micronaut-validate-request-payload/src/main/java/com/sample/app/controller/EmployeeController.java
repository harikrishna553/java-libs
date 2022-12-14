package com.sample.app.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.Valid;

import com.sample.app.dto.EmployeeRequest;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/employees")
public class EmployeeController {
	
	private static final AtomicInteger ID_COUNTER = new AtomicInteger(1);

	@Post
	@Consumes(MediaType.APPLICATION_JSON)
	public HttpResponse<Map<String, Object>> saveEmployee(@Valid final EmployeeRequest employeeRequest) {

		final Map<String, Object> response = new HashMap<>();
		response.put("id", ID_COUNTER.getAndIncrement());
		response.put("name", employeeRequest.getName());
		response.put("age", employeeRequest.getAge());
		
		return HttpResponse.status(HttpStatus.CREATED).body(response);
	}

}
