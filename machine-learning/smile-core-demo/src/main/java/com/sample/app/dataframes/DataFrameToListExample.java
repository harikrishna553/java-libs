package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;

import java.util.List;

public class DataFrameToListExample {
	public static void main(String[] args) {
		// Create a sample DataFrame
		String[] names = { "Alice", "Bob", "Charlie", "David" };
		int[] ages = { 25, 30, 35, 40 };
		DataFrame df = DataFrame.of(StringVector.of("Name", names), IntVector.of("Age", ages));

		// Convert DataFrame rows to List of Tuples
		List<Tuple> rowsList = df.toList();

		// Print each row from the List
		System.out.println("Rows as List:");
		for (Tuple row : rowsList) {
			System.out.println("Name: " + row.getString("Name") + ", Age: " + row.getInt("Age"));
		}

		// Example of indexed access to rows
		Tuple firstRow = rowsList.get(0); // Access the first row
		System.out.println("First Row: Name: " + firstRow.getString("Name") + ", Age: " + firstRow.getInt("Age"));
	}
}
