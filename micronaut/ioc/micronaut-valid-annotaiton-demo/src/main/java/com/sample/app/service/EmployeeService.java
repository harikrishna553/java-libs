package com.sample.app.service;

import javax.validation.Valid;

import com.sample.app.model.Employee;

import jakarta.inject.Singleton;

@Singleton
public class EmployeeService {

	public void registerEmployee(@Valid Employee employee) {
		System.out.println("Employee registered successfully " + employee);
	}
}
