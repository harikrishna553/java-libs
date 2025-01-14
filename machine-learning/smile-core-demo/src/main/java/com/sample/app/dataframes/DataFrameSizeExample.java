package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.vector.DoubleVector;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;

public class DataFrameSizeExample {
	public static void main(String[] args) {
		String[] names = { "Alice", "Bob", "Charlie", "Diana" };
		int[] ages = { 25, 30, 35, 40 };
		double[] salaries = { 50000.0, 60000.0, 70000.0, 80000.0 };
		String[] regions = { "North", "South", "East", "West" };

		// Create a DataFrame from the column data and column names
		DataFrame df = DataFrame.of(StringVector.of("Name", names), DoubleVector.of("Salary", salaries),
				IntVector.of("Age", ages), StringVector.of("Region", regions));

		// Print the DataFrame
		System.out.println("DataFrame:");
		System.out.println(df);

		// Get the number of rows using size()
		int numberOfRows = df.size();

		// Validate if DataFrame is empty
		if (numberOfRows == 0) {
			System.out.println("The DataFrame is empty.");
		} else {
			System.out.println("The DataFrame is not empty.");
			System.out.println("Number of rows in the DataFrame: " + numberOfRows);
		}
	}
}
