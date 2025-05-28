package com.sample.app.interfaces;

import com.sample.app.dto.Employee;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface EmployeeExtractor {
	@UserMessage("Extract information about an employee from following information.\n{{description}}")
	Employee extractPersonFrom(@V("description") String description);
}
