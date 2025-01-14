package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;

public class BooleanIndexingExample {
	public static void main(String[] args) {
		// Create a DataFrame with columns "ID" and "Name"
		DataFrame df = DataFrame.of(IntVector.of("ID", new int[] { 101, 102, 103, 104 }),
				StringVector.of("Name", new String[] { "Alice", "Bob", "Charlie", "Diana" }));

		System.out.println("Original DataFrame:");
		System.out.println(df);

		// Create a boolean array to filter rows
		boolean[] booleanIndex = { true, false, true, false };

		// Create a new DataFrame using boolean indexing
		DataFrame filteredRows = df.of(booleanIndex);

		System.out.println("\nDataFrame with Boolean Indexing:");
		System.out.println(filteredRows);
	}
}
