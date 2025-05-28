package com.sample.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.app.dto.Employee;
import com.sample.app.interfaces.EmployeeExtractor;

import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;

@Service
public class ExmployeeExtractionService {

	@Autowired
	private OllamaChatModel chatModel;

	public Employee getEmployee(String description) {
		EmployeeExtractor empExtractor = AiServices.create(EmployeeExtractor.class, chatModel);
		return empExtractor.extractPersonFrom(description);
	}

}
