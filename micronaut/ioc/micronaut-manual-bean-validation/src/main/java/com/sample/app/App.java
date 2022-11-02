package com.sample.app;

import com.sample.app.model.Employee;

import io.micronaut.context.ApplicationContext;
import io.micronaut.validation.validator.Validator;
import java.util.*;

import javax.validation.ConstraintViolation;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			Validator validator = applicationContext.getBean(Validator.class);
			Employee emp = new Employee();

			System.out.println("Validating employee with invalid data");

			emp.setName("");
			emp.setId(-1);
			emp.setAge(0);
			Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(emp);

			for (ConstraintViolation<Employee> constraintViolation : constraintViolations) {
				System.out.println("\t" + constraintViolation.getPropertyPath() + " -> " + constraintViolation.getMessage());
			}

			emp.setAge(34);
			emp.setId(1);
			emp.setName("Krishna");
			System.out.println("\nValidating employee with valid data");
			constraintViolations = validator.validate(emp);

			for (ConstraintViolation<Employee> constraintViolation : constraintViolations) {
				System.out.println("\t" + constraintViolation.getPropertyPath() + " -> " + constraintViolation.getMessage());
			}

			System.out.println("emp : " + emp);
		}

	}
}

