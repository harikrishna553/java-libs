package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;

public class DataFrameRowsAndColunmnsExample {
	public static void main(String[] args) {
		// Create a sample DataFrame
		String[] names = { "Alice", "Bob", "Charlie", "David" };
		int[] ages = { 25, 30, 35, 40 };
		DataFrame df = DataFrame.of(StringVector.of("Name", names), IntVector.of("Age", ages));

		// Get the number of rows using nrow() method
		int rowCount = df.nrow();
		System.out.println("Number of Rows: " + rowCount);

		// Get the number of columns using ncol() method
		int columnCount = df.ncol();
		System.out.println("Number of Columns: " + columnCount);
	}
}
