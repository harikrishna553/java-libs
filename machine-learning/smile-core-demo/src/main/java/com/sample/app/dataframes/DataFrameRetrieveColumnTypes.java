package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.type.DataType;
import smile.data.vector.DoubleVector;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;

public class DataFrameRetrieveColumnTypes {
	public static void main(String[] args) {
		// Create a DataFrame with sample data
		String[] names = { "Alice", "Bob", "Charlie" };
		int[] ages = { 25, 30, 35 };
		double[] salaries = { 50000.5, 60000.0, 70000.0 };

		DataFrame df = DataFrame.of(StringVector.of("Name", names), IntVector.of("Age", ages),
				DoubleVector.of("Salary", salaries));

		// Retrieve column data types
		DataType[] columnTypes = df.types();

		// Print column names with their respective data types
		System.out.println("Column Names and Data Types:");
		for (int i = 0; i < columnTypes.length; i++) {
			System.out.printf("%s: %s%n", df.column(i).name(), columnTypes[i]);
		}
	}
}
