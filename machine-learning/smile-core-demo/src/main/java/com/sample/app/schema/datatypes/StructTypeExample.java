package com.sample.app.schema.datatypes;

import java.util.ArrayList;
import java.util.List;

import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;

public class StructTypeExample {
	public static void main(String[] args) {
		// Define fields for the StructType
		StructField idField = new StructField("id", DataTypes.IntegerType);
		StructField nameField = new StructField("name", DataTypes.StringType);
		StructField ageField = new StructField("age", DataTypes.IntegerType);

		// Create a StructType (representing a structured record with id, name, and age)
		StructType schema = DataTypes.struct(idField, nameField, ageField);

		// Create data for the StructType
		List<Tuple> data = new ArrayList<>();
		data.add(Tuple.of(new Object[] { 1, "Alice", 30 }, schema));
		data.add(Tuple.of(new Object[] { 2, "Bob", 25 }, schema));
		data.add(Tuple.of(new Object[] { 3, "Charlie", 35 }, schema));

		// Create a DataFrame with the defined schema and data
		DataFrame df = DataFrame.of(data, schema);

		// Print the schema of the DataFrame
		System.out.println("Schema:");
		System.out.println(df.schema());

		// Print the data of the DataFrame
		System.out.println("Data:");
		System.out.println(df);

		// Retrieve and print the first row's data
		Tuple firstRow = df.get(0); // Get the first row
		int id = firstRow.getInt("id");
		String name = firstRow.getString("name");
		int age = firstRow.getInt("age");

		System.out.println("First Employee's Data:");
		System.out.println("ID: " + id);
		System.out.println("Name: " + name);
		System.out.println("Age: " + age);
	}
}
