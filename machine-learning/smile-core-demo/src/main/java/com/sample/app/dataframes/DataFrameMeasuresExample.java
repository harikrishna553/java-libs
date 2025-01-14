package com.sample.app.dataframes;

import java.text.NumberFormat;

import smile.data.DataFrame;
import smile.data.measure.Measure;
import smile.data.measure.NominalScale;
import smile.data.measure.RatioScale;
import smile.data.type.DataType;
import smile.data.type.DataTypes;
import smile.data.vector.DoubleVector;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;

public class DataFrameMeasuresExample {

	public static void main(String[] args) {
		// Define DataFrame columns with types
		DataType[] columnTypes = { DataTypes.StringType, // Name
				DataTypes.IntegerType, // Age
				DataTypes.DoubleType, // Salary
				DataTypes.StringType // Region
		};

		// Create specific NumberFormat instances for RatioScale
		RatioScale ratioScaleCurrency = new RatioScale(NumberFormat.getCurrencyInstance()); // for monetary values
		RatioScale ratioScalePercent = new RatioScale(NumberFormat.getPercentInstance()); // for percentage values

		Measure[] columnMeasures = { new NominalScale(), // Name
				ratioScaleCurrency, // Age (assuming currency format for demonstration)
				ratioScalePercent, // Salary (assuming percent format for demonstration)
				new NominalScale() // Region
		};

		// Create data as arrays with the correct type for each column
		String[] names = { "Alice", "Bob", "Charlie", "Diana" };
		int[] ages = { 25, 30, 35, 40 };
		double[] salaries = { 50000.0, 60000.0, 70000.0, 80000.0 };
		String[] regions = { "North", "South", "East", "West" };

		// Create a DataFrame from the column data and column names
		DataFrame df = DataFrame.of(StringVector.of("Name", names), DoubleVector.of("Salary", salaries),
				IntVector.of("Age", ages), StringVector.of("Region", regions));

		//df.schema().measures();
		
		Measure[] measures = df.measures();
		for (Measure measure : measures) {
			System.out.println(measure);
		}

		// Optionally: Print the DataFrame to verify the data
		System.out.println("DataFrame:");
		System.out.println(df);
	}
}
