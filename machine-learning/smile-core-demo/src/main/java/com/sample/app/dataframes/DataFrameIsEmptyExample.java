package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.vector.IntVector;

public class DataFrameIsEmptyExample {
	public static void main(String[] args) {
		// Create an empty DataFrame
		DataFrame emptyDf = DataFrame.of(IntVector.of("Age", new int[] {}));

		// Create a non-empty DataFrame
		int[] ages = { 25, 30, 35, 40 };
		DataFrame nonEmptyDf = DataFrame.of(IntVector.of("Age", ages));

		// Check if the DataFrames are empty
		System.out.println("Is the first DataFrame empty? " + emptyDf.isEmpty());
		System.out.println("Is the second DataFrame empty? " + nonEmptyDf.isEmpty());
	}
}
