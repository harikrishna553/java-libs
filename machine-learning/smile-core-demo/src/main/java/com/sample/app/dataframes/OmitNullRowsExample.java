package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;

public class OmitNullRowsExample {
	public static void main(String[] args) {
		// Create a sample DataFrame with a missing value in the "Age" column
		String[] names = { "Alice", "Bob", "Charlie", null }; // 4th name is null
		int[] ages = { 25, 41, 35, 40 };

		DataFrame df = DataFrame.of(StringVector.of("Name", names), IntVector.of("Age", ages));

		// Print original DataFrame
		System.out.println("Original DataFrame:");
		df.stream()
				.forEach(row -> System.out.println("Name: " + row.getString("Name") + ", Age: " + row.getInt("Age")));

		// Get DataFrame without rows containing null values
		DataFrame dfNoNulls = df.omitNullRows();

		// Print DataFrame after omitting rows with null values
		System.out.println("\nDataFrame After Omitting Null Rows:");
		dfNoNulls.stream()
				.forEach(row -> System.out.println("Name: " + row.getString("Name") + ", Age: " + row.getInt("Age")));
	}
}
