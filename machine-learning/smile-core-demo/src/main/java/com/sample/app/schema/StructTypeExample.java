package com.sample.app.schema;

import smile.data.Tuple;
import smile.data.measure.Measure;
import smile.data.measure.NominalScale;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;

public class StructTypeExample {
	public static void main(String[] args) {
		// Define individual StructFields
		StructField nameField = new StructField("Name", DataTypes.StringType);
		StructField ageField = new StructField("Age", DataTypes.IntegerType);
		StructField salaryField = new StructField("Salary", DataTypes.DoubleType);
		Measure genderScale = new NominalScale("Male", "Female", "Other");
		StructField genderField = new StructField("Gender", DataTypes.IntegerType, genderScale);

		// Create a StructType schema
		StructType employeeSchema = new StructType(nameField, ageField, salaryField, genderField);

		// Display the schema
		System.out.println("Employee Schema: " + employeeSchema);

		// Create a tuple with the schema
		Tuple employee = Tuple.of(new Object[] { "John Doe", 30, 50000.0, 0 }, employeeSchema);

		// Access tuple fields
		System.out.println("Name: " + employee.get("Name"));
		System.out.println("Age: " + employee.get("Age"));
		System.out.println("Salary: " + employee.get("Salary"));
		System.out.println("Gender: " + employee.get("Gender"));
		System.out.println("Gender Label: " + employee.getScale("Gender"));
	}
}
