package com.sample.app.dataframes;

import java.util.List;

import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;
import smile.data.vector.BaseVector;

public class GetVectorByColumnName {
	public static void main(String[] args) {

		// Define corresponding data types
		StructField[] structFields = new StructField[] { new StructField("ID", DataTypes.IntegerType),
				new StructField("NAME", DataTypes.StringType) };

		// Create StructType
		StructType schema = new StructType(structFields);

		// Create a DataFrame with sample employee data
		List<Tuple> rows = List.of(Tuple.of(new Object[] { 1, "Alice" }, schema),
				Tuple.of(new Object[] { 2, "Bob" }, schema), Tuple.of(new Object[] { 3, "Charlie" }, schema));

		DataFrame df = DataFrame.of(rows);

		// Print the schema
		System.out.println("Schema: " + df.schema());

		// Print the DataFrame
		System.out.println("DataFrame: \n" + df);

		BaseVector idColumnData = df.column("ID");
		BaseVector nameColumnData = df.apply("NAME");

		System.out.println("idColumnData: \n" + idColumnData);
		System.out.println("\nnameColumnData: \n" + nameColumnData);

	}
}
