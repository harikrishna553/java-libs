package com.sample.app.tuple;

import smile.data.Tuple;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;

public class TupleCreation3 {
	public static void main(String[] args) {
		// Define the schema for a row of integers
		StructField id = new StructField("id", DataTypes.IntegerType);
		StructField age = new StructField("age", DataTypes.IntegerType);
		StructType schema = DataTypes.struct(id, age);

		// Create a Tuple from an integer array
		Tuple tuple = Tuple.of(new int[] { 12345, 34 }, schema);

		// Print the Tuple
		System.out.println(tuple);
	}
}
