package com.sample.app.schema;

import java.util.ArrayList;
import java.util.List;

import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;

public class NestedStructType {
	public static void main(String[] args) {

		// Define the fields for the 'contactDetails' struct
		StructField[] contactFields = new StructField[] { new StructField("phone", DataTypes.StringType),
				new StructField("email", DataTypes.StringType) };

		// Create the 'contactDetails' struct type
		StructType contactDetailsType = new StructType(contactFields);

		// Define the fields for the Employee struct
		StructField[] employeeFields = new StructField[] { new StructField("id", DataTypes.IntegerType),
				new StructField("name", DataTypes.StringType), new StructField("contactDetails", contactDetailsType) };

		// Create the Employee struct type
		StructType employeeType = new StructType(employeeFields);

		// Create a list of employees with structured 'contactDetails'
		List<Tuple> employees = new ArrayList<>();
		employees.add(Tuple.of(
				new Object[] { 1, "Alice",
						Tuple.of(new Object[] { "123-456-7890", "alice@example.com" }, contactDetailsType) },
				employeeType));
		employees.add(Tuple.of(
				new Object[] { 2, "Bob",
						Tuple.of(new Object[] { "987-654-3210", "bob@example.com" }, contactDetailsType) },
				employeeType));

		// Create a DataFrame
		DataFrame df = DataFrame.of(employees);

		// Print the schema
		System.out.println("Schema:");
		System.out.println(df.schema());

		// Print the DataFrame
		System.out.println("Data:");
		System.out.println(df);

		// Retrieve the 'contactDetails' field for the first employee using getStruct
		Tuple contactDetails = df.getStruct(0, "contactDetails");

		// Access individual fields within the Tuple
		String phone = contactDetails.getString("phone");
		String email = contactDetails.getString("email");

		// Print the details
		System.out.println("First Employee Contact Details:");
		System.out.println("Phone: " + phone);
		System.out.println("Email: " + email);

	}
}
