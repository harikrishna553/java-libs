package com.sample.app.dataframes;

import java.util.List;

import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;
import smile.data.vector.BaseVector;

public class DataFrameColumnByIndex {
    public static void main(String[] args) {
        // Define the schema
        StructField[] structFields = new StructField[] {
            new StructField("ID", DataTypes.IntegerType),
            new StructField("NAME", DataTypes.StringType)
        };

        StructType schema = new StructType(structFields);

        // Create sample data
        List<Tuple> rows = List.of(
            Tuple.of(new Object[]{1, "Alice"}, schema),
            Tuple.of(new Object[]{2, "Bob"}, schema),
            Tuple.of(new Object[]{3, "Charlie"}, schema)
        );

        // Create a DataFrame
        DataFrame df = DataFrame.of(rows);

        // Print the DataFrame
        System.out.println("DataFrame: \n" + df);

        // Retrieve the first column (index 0)
        BaseVector idColumn = df.column(0);
        System.out.println("ID Column: " + idColumn);

        // Retrieve the second column (index 1)
        BaseVector nameColumn = df.column(1);
        System.out.println("Name Column: " + nameColumn);
    }
}
