package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;

public class DataFrameGetExample {
	public static void main(String[] args) {
		// Create a sample DataFrame
		String[] names = { "Alice", "Bob", "Charlie" };
		int[] ages = { 25, 30, 35 };
		DataFrame df = DataFrame.of(StringVector.of("Name", names), IntVector.of("Age", ages));

		// Retrieve a specific row using get()
		Tuple row = df.get(1); // Get the second row (index starts at 0)
		System.out.println("Retrieved Row: " + row);

		// Access elements from the Tuple
		String name = row.getString("Name");
		int age = row.getInt("Age");
		System.out.println("Name: " + name + ", Age: " + age);

		// Check for null values (example scenario)
		if (row.isNullAt("Age")) {
			System.out.println("Age is null.");
		} else {
			System.out.println("Age is not null.");
		}
	}
}
