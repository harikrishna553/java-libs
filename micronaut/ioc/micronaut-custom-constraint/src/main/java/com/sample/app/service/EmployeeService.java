package com.sample.app.service;

import com.sample.app.constraint.EmployeeNameConstraint;

import jakarta.inject.Singleton;

@Singleton
public class EmployeeService {

	public void registerEmployee(
			@EmployeeNameConstraint(minSize = 2, regex = "[a-zA-Z ]*", message = "Employee name must be >= 2 characters and contain only a-z, A-Z characters") String name) {
		System.out.println("Employee registered successfully " + name);
	}
}
