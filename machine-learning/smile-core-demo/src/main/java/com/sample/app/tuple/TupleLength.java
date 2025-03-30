package com.sample.app.tuple;

import smile.data.Tuple;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;

public class TupleLength {
	public static void main(String[] args) {
		// Define the schema for the tuple
		StructField idField = new StructField("EmployeeID", DataTypes.IntegerType);
		StructField nameField = new StructField("FirstName", DataTypes.StringType);
		StructField departmentField = new StructField("Department", DataTypes.StringType);
		StructType schema = DataTypes.struct(idField, nameField, departmentField);

		// Create a tuple with the schema
		Object[] row = { 1, "John", "Marketing" };
		Tuple tuple = Tuple.of(row, schema);

		// Print the tuple and its length
		System.out.println("Tuple: " + tuple);
		System.out.println("Number of elements in the tuple: " + tuple.length());
	}
}
