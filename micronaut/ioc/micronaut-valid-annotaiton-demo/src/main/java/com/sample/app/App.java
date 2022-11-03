package com.sample.app;

import com.sample.app.model.Employee;
import com.sample.app.service.EmployeeService;

import io.micronaut.context.ApplicationContext;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {
			EmployeeService employeeService = applicationContext.getBean(EmployeeService.class);
			
			Employee emp = new Employee();
			emp.setAge(5);
			emp.setId(1);
			emp.setName("Krishna");
			employeeService.registerEmployee(emp);
		}

	}
}
