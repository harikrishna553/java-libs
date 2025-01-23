package com.sample.app.schema.datatypes;

import java.util.ArrayList;
import java.util.List;

import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;

public class ArrayTypeExample {
	public static void main(String[] args) {
		// Define fields for ArrayType (Array of Integer)
		StructField arrayField = new StructField("numbers", DataTypes.array(DataTypes.IntegerType));

		// Create the schema using DataTypes.struct() to group the fields
		StructType schema = DataTypes.struct(arrayField);

		// Create data for the array of integers
		List<Tuple> data = new ArrayList<>();
		data.add(Tuple.of(new Object[] { new int[] { 1, 2, 3, 4, 5 } }, schema));
		data.add(Tuple.of(new Object[] { new int[] { 6, 7, 8, 9, 10 } }, schema));
		data.add(Tuple.of(new Object[] { new int[] { 11, 12, 13, 14, 15 } }, schema));

		// Create a DataFrame with the defined schema and data
		DataFrame df = DataFrame.of(data, schema);

		// Print the schema of the DataFrame
		System.out.println("Schema:");
		System.out.println(df.schema());

		// Print the data of the DataFrame
		System.out.println("Data:");
		System.out.println(df);

		// Retrieve and print the first row's array data
		Tuple firstRow = df.get(0); // Get the first row
		System.out.println("first row : " + firstRow);

		System.out.println("\nAll the Elements one by one");
		for (int number : (int[]) firstRow.get("numbers")) {
			System.out.println(number);
		}

	}
}
