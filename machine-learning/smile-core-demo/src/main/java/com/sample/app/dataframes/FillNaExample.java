package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.vector.DoubleVector;
import smile.data.vector.IntVector;

public class FillNaExample {
	public static void main(String[] args) {
		// Create a sample DataFrame with NaN values in the "Value" column
		double[] values = { 10.5, Double.NaN, 20.3, Double.POSITIVE_INFINITY }; // NaN and Inf values
		int[] ids = { 1, 2, 3, 4 };

		DataFrame df = DataFrame.of(IntVector.of("ID", ids), DoubleVector.of("Value", values));

		// Print original DataFrame
		System.out.println("Original DataFrame:");
		df.stream()
				.forEach(row -> System.out.println("ID: " + row.getInt("ID") + ", Value: " + row.getDouble("Value")));

		// Fill NaN/Inf values with a specified value (e.g., 10.0)
		DataFrame dfFilled = df.fillna(10.0);

		// Print DataFrame after filling NaN/Inf values
		System.out.println("\nDataFrame After Filling NaN/Inf Values:");
		dfFilled.stream()
				.forEach(row -> System.out.println("ID: " + row.getInt("ID") + ", Value: " + row.getDouble("Value")));
	}
}
