package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;

public class DataFrameAccessACellByIndex {
	public static void main(String[] args) {
		// Sample DataFrame with two columns: "ID" (int) and "Name" (String)
		DataFrame df = DataFrame.of(IntVector.of("ID", new int[] { 1, 2, 3 }),
				StringVector.of("Name", new String[] { "Alice", "Bob", "Charlie" }));

		// Retrieve and print the value in the 2nd row (index 1) and 1st column (index
		// 0)
		Object cellValue = df.get(1, 0); // Expected: 2 (ID value)
		System.out.println("Cell value at (1, 0): " + cellValue);

		// Retrieve and print the value in the 3rd row (index 2) and 2nd column (index
		// 1)
		cellValue = df.get(2, 1); // Expected: Charlie (Name value)
		System.out.println("Cell value at (2, 1): " + cellValue);
	}
}
