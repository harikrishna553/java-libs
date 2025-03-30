package com.sample.app.tuple;

import smile.data.Tuple;
import smile.data.type.DataType;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;

public class TupleFieldDataTypesExample {
	public static void main(String[] args) {
		// Define the schema with field names and data types
		StructField idField = new StructField("EmployeeID", DataTypes.IntegerType);
		StructField nameField = new StructField("FirstName", DataTypes.StringType);
		StructField deptField = new StructField("Department", DataTypes.StringType);
		StructType schema = DataTypes.struct(idField, nameField, deptField);

		// Create a tuple with data
		Object[] row = { 101, "John Doe", "Engineering" };
		Tuple tuple = Tuple.of(row, schema);

		// Retrieve field data types using the types() method
		DataType[] fieldDataTypes = tuple.types();

		// Print field data types
		System.out.println("Field Data Types in the Tuple:");
		for (DataType dataType : fieldDataTypes) {
			System.out.println(dataType);
		}
	}
}
