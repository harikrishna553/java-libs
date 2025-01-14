package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;

public class DataFrameFilterRowsByRowIndex {
	public static void main(String[] args) {
		// Create a DataFrame with columns "ID" and "Name"
		DataFrame df = DataFrame.of(IntVector.of("ID", new int[] { 101, 102, 103, 104 }),
				StringVector.of("Name", new String[] { "Alice", "Bob", "Charlie", "Diana" }));

		System.out.println("Original DataFrame:");
		System.out.println(df);

		// Create a new DataFrame with selected rows (indices 0 and 2)
		DataFrame selectedRows = df.of(0, 2);

		System.out.println("\nDataFrame with Selected Rows (indices 0 and 2):");
		System.out.println(selectedRows);
	}
}
