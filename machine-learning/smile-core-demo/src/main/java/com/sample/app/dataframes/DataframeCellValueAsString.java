package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;

public class DataframeCellValueAsString {
	public static void main(String[] args) {
		// Create a sample DataFrame with a missing value in the "Age" column
		String[] names = { "Alice", "Bob", "Charlie", null }; // 4th name is null
		int[] ages = { 25, 41, 35, 40 };

		DataFrame df = DataFrame.of(StringVector.of("Name", names), IntVector.of("Age", ages));

		// Retrieve and print cell values as strings using row and column indices
		System.out.println("Value at (0, 0): " + df.toString(0, 0)); // "Alice"
		System.out.println("Value at (1, 1): " + df.toString(1, 1)); // "41"

		// Retrieve and print cell values as strings using row index and column name
		System.out.println("Value at row 0, column 'Name': " + df.toString(0, "Name")); // "Alice"
		System.out.println("Value at row 1, column 'Age': " + df.toString(1, "Age")); // "41"

	}
}
