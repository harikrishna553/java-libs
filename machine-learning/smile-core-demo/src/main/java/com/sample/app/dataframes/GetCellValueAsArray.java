package com.sample.app.dataframes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import smile.data.DataFrame;

public class GetCellValueAsArray {

	public static class Employee {
		private int id;
		private String name;
		private String[] hobbies;

		// Parameterized Constructor
		public Employee(int id, String name, List<String> hobbies) {
			this.id = id;
			this.name = name;
			this.hobbies = hobbies.toArray(new String[] {});
		}

		// Getters and Setters
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String[] getHobbies() {
			return hobbies;
		}

		public void setHobbies(String[] hobbies) {
			this.hobbies = hobbies;
		}

		// toString Method
		@Override
		public String toString() {
			return "Employee{" + "id=" + id + ", name='" + name + '\'' + ", hobbies=" + hobbies + '}';
		}
	}

	public static void main(String[] args) {
		// Create a list of hobbies
		List<String> hobbies1 = Arrays.asList("Reading", "Swimming", "Cooking");
		List<String> hobbies2 = Arrays.asList("Cycling", "Gaming", "Traveling");
		List<String> hobbies3 = Arrays.asList("Painting", "Running", "Hiking");

		// Create employee objects
		Employee emp1 = new Employee(1, "Alice", hobbies1);
		Employee emp2 = new Employee(2, "Bob", hobbies2);
		Employee emp3 = new Employee(3, "Charlie", hobbies3);

		List<Employee> employees = new ArrayList<>();
		employees.add(emp1);
		employees.add(emp2);
		employees.add(emp3);

		// Create a DataFrame using the Employee class
		DataFrame df = DataFrame.of(employees, Employee.class);

		// Print the schema of the DataFrame
		System.out.println("Schema: ");
		System.out.println(df.schema());

		// Print the first few rows of the DataFrame
		System.out.println("Data: ");
		System.out.println(df);

		// Retrieve the 'hobbies' array for the first employee using column name
		System.out.println("Hobbies of Employee Alice are :");
		String[] hobbies = df.getArray(0, "hobbies");
		for (String hobby : hobbies) {
			System.out.println(hobby);
		}

	}
}
