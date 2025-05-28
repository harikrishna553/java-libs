package com.sample.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.app.dto.Employee;
import com.sample.app.service.ExmployeeExtractionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/employee-extractor")
@CrossOrigin("*")
@Tag(name = "Employee Controller", description = "This section contains APIs related to Employee APIs Powered by Ollama")
public class EmployeeController {

	@Autowired
	private ExmployeeExtractionService employeeExtractionService;

	@PostMapping
	public Employee chat(@RequestBody @Valid RequestPayload requestPayload) {
		return employeeExtractionService.getEmployee(requestPayload.getDescription());
	}

	private static class RequestPayload {
		@NotEmpty
		private String description;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	}
}
