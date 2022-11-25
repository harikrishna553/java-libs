package com.sample.app.controller;

import com.sample.app.model.Employee;
import com.sample.app.service.EmployeeService;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.CustomHttpMethod;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import jakarta.inject.Inject;

@Controller("/employees")
public class EmployeeController {

	@Inject
	private EmployeeService employeeService;

	@CustomHttpMethod(method = "LOCK", value = "/lock-employee/{employeeId}")
	public Employee lockEmployee(@NonNull @PathVariable(name = "employeeId") final Integer empId) {
		return employeeService.lockEmployee(empId);
	}
	
	@CustomHttpMethod(method = "UNLOCK", value = "/unlock-employee/{employeeId}")
	public Employee unlockEmployee(@NonNull @PathVariable(name = "employeeId") final Integer empId) {
		return employeeService.unlockEmployee(empId);
	}

	@Get("/{employeeId}")
	public Employee byId(@NonNull @PathVariable(name = "employeeId") final Integer empId) {
		return employeeService.byId(empId);
	}

}