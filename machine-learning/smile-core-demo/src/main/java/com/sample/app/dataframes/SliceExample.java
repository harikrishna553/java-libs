package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;

public class SliceExample {
	public static void main(String[] args) {
		// Create a DataFrame with columns "ID" and "Name"
		DataFrame df = DataFrame.of(IntVector.of("ID", new int[] { 101, 102, 103, 104, 105 }),
				StringVector.of("Name", new String[] { "Alice", "Bob", "Charlie", "Diana", "Eve" }));

		System.out.println("Original DataFrame:");
		System.out.println(df);

		// Slice rows from index 1 to 4 (exclusive)
		DataFrame slicedRows = df.slice(1, 4);

		System.out.println("\nSliced DataFrame (Rows 1 to 3):");
		System.out.println(slicedRows);
	}
}
