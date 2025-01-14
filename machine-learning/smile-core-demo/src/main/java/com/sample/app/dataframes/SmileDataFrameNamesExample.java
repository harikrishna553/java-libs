package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;

public class SmileDataFrameNamesExample {
	public static void main(String[] args) {
		// Create a sample DataFrame
		String[] names = { "Alice", "Bob", "Charlie" };
		int[] ages = { 24, 30, 28 };
		String[] cities = { "New York", "San Francisco", "Los Angeles" };

		DataFrame df = DataFrame.of(StringVector.of("Name", names), IntVector.of("Age", ages),
				StringVector.of("City", cities));

		// Print the DataFrame
		System.out.println("Original DataFrame:");
		System.out.println(df);

		// Retrieve column names
		String[] columnNames = df.names();

		// Print column names
		System.out.println("\nColumn Names:");
		for (String columnName : columnNames) {
			System.out.println(columnName);
		}

		// Use column names for dynamic operations
		System.out.println("\nUsing Column Names to Access Data:");
		for (String columnName : columnNames) {
			System.out.println("Values in the column " + columnName);
			for (int i = 0; i < df.size(); i++) {
				System.out.printf("\t%s%n", df.column(columnName).get(i));
			}

		}
	}
}
