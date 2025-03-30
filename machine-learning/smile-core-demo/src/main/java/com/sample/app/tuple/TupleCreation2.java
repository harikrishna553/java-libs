package com.sample.app.tuple;

import smile.data.Tuple;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;

public class TupleCreation2 {
	public static void main(String[] args) {
		// Define the schema for a row of doubles
		StructField height = new StructField("height", DataTypes.DoubleType);
		StructField weight = new StructField("weight", DataTypes.DoubleType);
		StructType schema = DataTypes.struct(height, weight);

		// Create a Tuple from a double array
		Tuple tuple = Tuple.of(new double[] { 5.7, 65 }, schema);

		// Print the Tuple
		System.out.println("tuple : " + tuple);
		System.out.println("\nHeight : " + tuple.get("height"));
		System.out.println("Weight : " + tuple.get("weight"));
	}
}
