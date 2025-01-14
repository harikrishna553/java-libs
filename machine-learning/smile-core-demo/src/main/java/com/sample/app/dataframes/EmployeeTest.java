package com.sample.app.dataframes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.sample.app.model.Employee;

import smile.data.DataFrame;

public class EmployeeTest {
	public static void main(String[] args) {
		// Create some sample employees
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee(1, "Alice", true, LocalDateTime.of(2020, 5, 1, 9, 30), new BigDecimal("60000.50")));
		employees.add(new Employee(2, "Bob", false, LocalDateTime.of(2021, 6, 15, 10, 0), new BigDecimal("55000.75")));
		employees.add(
				new Employee(3, "Charlie", true, LocalDateTime.of(2019, 7, 10, 8, 15), new BigDecimal("75000.00")));

		// Create a DataFrame using the Employee class
		DataFrame df = DataFrame.of(employees, Employee.class);

		// Print schema of the DataFrame
		System.out.println("Schema: ");
		System.out.println(df.schema());

		// Print the first few rows of the DataFrame
		System.out.println("Data: ");
		System.out.println(df);

		// Retrieve and print the 'hireDate' using getDateTime
		LocalDateTime hireDate = df.getDateTime(0, "hireDate");
		System.out.println("First Employee Hire Date: " + hireDate);

		// Retrieve and print the 'salary' using getDecimal
		BigDecimal salary = df.getDecimal(0, "salary");
		System.out.println("First Employee Salary: " + salary);
	}
}
