package com.sample.app.tuple;

import smile.data.Tuple;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;

public class TupleCreation1 {
	public static void main(String[] args) {

		// Define the fields of the schema
		StructField idField = new StructField("id", DataTypes.IntegerType);
		StructField nameField = new StructField("name", DataTypes.StringType);

		StructField mail = new StructField("mail", DataTypes.IntegerType);
		StructField phone = new StructField("phone", DataTypes.StringType);
		StructType contactDetails = DataTypes.struct(mail, phone);

		// Create the schema
		StructType schema = DataTypes.struct(idField, nameField, new StructField("contactDetails", contactDetails));

		// Create a Tuple from an object array
		Tuple tuple = Tuple.of(
				new Object[] { 1, "Alice", Tuple.of(new Object[] { "test@test.com", 123456 }, contactDetails) },
				schema);

		// Print the Tuple
		System.out.println("id " + tuple.get("id"));
		System.out.println("name " + tuple.get("name"));

		Tuple contactDetailsTuple = (Tuple) tuple.get("contactDetails");

		System.out.println("mail " + contactDetailsTuple.get("mail"));
		System.out.println("phone " + contactDetailsTuple.get("phone"));
	}

}
