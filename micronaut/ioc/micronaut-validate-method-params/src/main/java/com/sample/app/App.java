package com.sample.app;

import com.sample.app.model.Employee;

import io.micronaut.context.ApplicationContext;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			Employee emp = applicationContext.getBean(Employee.class);

			System.out.println("Set employee name to blank");
			try {
				emp.setName("");
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("\nSet employee id to -1");
			try {
				emp.setId(-1);
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("\nSet employee age to 0");
			try {
				emp.setAge(0);
			} catch (Exception e) {
				e.printStackTrace();
			}

			emp.setAge(34);
			emp.setId(1);
			emp.setName("Krishna");

			System.out.println("\nemp : " + emp);
		}

	}
}