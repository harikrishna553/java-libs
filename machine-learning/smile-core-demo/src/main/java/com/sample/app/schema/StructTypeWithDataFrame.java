package com.sample.app.schema;

import java.util.stream.Stream;

import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;

public class StructTypeWithDataFrame {
	public static void main(String[] args) {
		StructType schema = new StructType(new StructField("name", DataTypes.StringType),
				new StructField("age", DataTypes.IntegerType), new StructField("isMember", DataTypes.BooleanType));

		Stream<Tuple> data = Stream.of(Tuple.of(new Object[] { "Alice", 30, true }, schema),
				Tuple.of(new Object[] { "Bob", 25, false }, schema),
				Tuple.of(new Object[] { "Charlie", 35, true }, schema));

		DataFrame df = DataFrame.of(data, schema);

		System.out.println("DataFrame:\n" + df);
	}
}
