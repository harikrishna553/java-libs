package com.sample.app.tuples;

import smile.data.Tuple;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;

public class HelloTuple {
	public static void main(String[] args) {
		// Define the schema for the Tuple
		StructType schema = new StructType(new StructField("name", DataTypes.StringType),
				new StructField("age", DataTypes.IntegerType), new StructField("salary", DataTypes.DoubleType));

		// Create a Tuple instance
		Tuple employee = Tuple.of(new Object[] { "John Doe", 30, null }, schema);

		// Access values generically
		System.out.println("\nName (generic access): " + employee.get("name"));
		System.out.println("Age (generic access): " + employee.get("age"));

		// Access primitive types directly
		int age = employee.getInt(1);
		System.out.println("\nAge (primitive access): " + age);

		// Check for null values before accessing
		if (employee.isNullAt(2)) {
			System.out.println("Salary is not available.");
		} else {
			double salary = employee.getDouble(2);
			System.out.println("Salary: " + salary);
		}

		// Print the schema and data
		System.out.println("\nSchema: " + schema);
		System.out.println("\nTuple data: " + employee);
	}
}
