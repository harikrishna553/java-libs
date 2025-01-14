package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;

public class DataFrameStructureExample {
	public static void main(String[] args) {
		// Create a sample DataFrame
		String[] names = { "Alice", "Bob", "Charlie", "David" };
		int[] ages = { 25, 30, 35, 40 };
		DataFrame df = DataFrame.of(StringVector.of("Name", names), IntVector.of("Age", ages));

		// Get the structure of the DataFrame
		DataFrame structure = df.structure();

		// Print the structure of the DataFrame
		System.out.println("DataFrame Structure:");
		structure.stream().forEach(row -> System.out.println("Column: " + row.getString("Column") + ", Type: "
				+ row.getString("Type") + ", Measure: " + row.getString("Measure")));
	}
}
