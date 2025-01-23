package com.sample.app.schema;

import smile.data.Tuple;
import smile.data.measure.Measure;
import smile.data.measure.NominalScale;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;

public class StructFieldExample {
	private static void printEmployeeTuple(Tuple employee) {
		// Access and display Tuple values generically
		System.out.println("\nEmployee Name: " + employee.get("Name"));
		System.out.println("Employee Age: " + employee.get("Age"));
		System.out.println("Employee Salary: " + employee.get("Salary"));
		System.out.println("Employee Gender: " + employee.get("Gender"));
		System.out.println("Employee Gender Actual Label: " + employee.getScale("Gender"));
	}

	public static void main(String[] args) {
		// Define individual StructFields
		StructField nameField = new StructField("Name", DataTypes.StringType);
		StructField ageField = new StructField("Age", DataTypes.IntegerType);
		StructField salaryField = new StructField("Salary", DataTypes.DoubleType);

		// Define a StructField with a nominal scale measure
		Measure genderScale = new NominalScale("Male", "Female", "Other");
		StructField genderField = new StructField("Gender", DataTypes.IntegerType, genderScale);

		// Create a StructType schema from StructFields
		StructType employeeSchema = new StructType(nameField, ageField, salaryField, genderField);

		// Display the schema
		System.out.println("Employee Schema: " + employeeSchema);

		// Define a Tuple with the schema
		Tuple employee1 = Tuple.of(new Object[] { "John Doe", 30, 45000.5, 0 }, employeeSchema);
		Tuple employee2 = Tuple.of(new Object[] { "Sailaja", 37, 95000.5, 1 }, employeeSchema);

		printEmployeeTuple(employee1);
		printEmployeeTuple(employee2);
	}
}
