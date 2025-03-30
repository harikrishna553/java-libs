package com.sample.app.tuple;

import smile.data.Tuple;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;

public class TupleFieldNamesExample {
	public static void main(String[] args) {
		// Define the schema
		StructField idField = new StructField("EmployeeID", DataTypes.IntegerType);
		StructField nameField = new StructField("FirstName", DataTypes.StringType);
		StructField deptField = new StructField("Department", DataTypes.StringType);
		StructType schema = DataTypes.struct(idField, nameField, deptField);

		// Create a tuple with data
		Object[] row = { 101, "John Doe", "Engineering" };
		Tuple tuple = Tuple.of(row, schema);

		// Retrieve field names using the names() method
		String[] fieldNames = tuple.names();

		// Print field names
		System.out.println("Field Names in the Tuple:");
		for (String fieldName : fieldNames) {
			System.out.println(fieldName);
		}
	}
}
