package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;

public class IsNullAtExample {
	public static void main(String[] args) {
		// Create a DataFrame with columns "ID" and "Name"
		DataFrame df = DataFrame.of(IntVector.of("ID", new int[] { 101, 102, 103 }),
				StringVector.of("Name", new String[] { "Alice", null, "Charlie" }));

		System.out.println("Original DataFrame:");
		System.out.println(df);

		// Check for null using row and column index
		boolean isNullIndex = df.isNullAt(1, 1);
		System.out.println("\nIs cell (1, 1) null? " + isNullIndex);

		// Check for null using row index and column name
		boolean isNullName = df.isNullAt(1, "Name");
		System.out.println("Is cell at row 1, column 'Name' null? " + isNullName);

		// Check for null using row and column index
		isNullIndex = df.isNullAt(2, 0);
		System.out.println("\nIs cell (2, 0) null? " + isNullIndex);

		// Check for null using row index and column name
		isNullName = df.isNullAt(2, "Name");
		System.out.println("Is cell at row 2, column 'Name' null? " + isNullName);
	}
}
