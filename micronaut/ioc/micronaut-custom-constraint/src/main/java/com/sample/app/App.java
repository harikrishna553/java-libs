package com.sample.app;

import com.sample.app.model.Employee;
import com.sample.app.service.EmployeeService;

import io.micronaut.context.ApplicationContext;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {
			EmployeeService employeeService = applicationContext.getBean(EmployeeService.class);

			Employee emp = new Employee();
			emp.setAge(25);
			emp.setId(1);
			emp.setName("a");
			employeeService.registerEmployee("a");
		}

	}
}
