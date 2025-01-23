package com.sample.app.schema.datatypes;

import java.util.ArrayList;
import java.util.List;

import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;

public class PrimitiveDataTypesExample {
	public static void main(String[] args) {
		// Define the fields for each primitive type
		StructField booleanField = new StructField("isTrue", DataTypes.BooleanType);
		StructField byteField = new StructField("byteValue", DataTypes.ByteType);
		StructField charField = new StructField("charValue", DataTypes.CharType);
		StructField shortField = new StructField("shortValue", DataTypes.ShortType);
		StructField intField = new StructField("intValue", DataTypes.IntegerType);
		StructField longField = new StructField("longValue", DataTypes.LongType);
		StructField floatField = new StructField("floatValue", DataTypes.FloatType);
		StructField doubleField = new StructField("doubleValue", DataTypes.DoubleType);

		// Create a list of fields for the StructType
		List<StructField> fields = new ArrayList<>();
		fields.add(booleanField);
		fields.add(byteField);
		fields.add(charField);
		fields.add(shortField);
		fields.add(intField);
		fields.add(longField);
		fields.add(floatField);
		fields.add(doubleField);

		// Use DataTypes.struct() to create a StructType
		StructType schema = DataTypes.struct(fields);

		// Create data for each of the columns
		List<Tuple> data = new ArrayList<>();
		data.add(Tuple.of(
				new Object[] { true, (byte) 100, 'A', (short) 3000, 123456, 9876543210L, 3.14f, 3.141592653589793 },
				schema));
		data.add(Tuple.of(
				new Object[] { false, (byte) 50, 'B', (short) 2000, 654321, 1234567890L, 2.71f, 2.718281828459045 },
				schema));
		data.add(Tuple.of(
				new Object[] { true, (byte) 25, 'C', (short) 1000, 987654, 1122334455L, 1.61f, 1.618033988749895 },
				schema));

		// Create a DataFrame with the data and schema
		DataFrame df = DataFrame.of(data);

		// Print the schema
		System.out.println("Schema of DataFrame:");
		System.out.println(df.schema());

		// Print the DataFrame
		System.out.println("\nDataFrame:");
		System.out.println(df);
	}
}
