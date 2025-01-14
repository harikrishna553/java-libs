package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;

public class DataFrameAccessByRowIndexAndColumnName {
    public static void main(String[] args) {
        // Create a DataFrame with columns "ID" and "Name"
        DataFrame df = DataFrame.of(
            IntVector.of("ID", new int[] {101, 102, 103}),
            StringVector.of("Name", new String[] {"Alice", "Bob", "Charlie"})
        );

        // Retrieve and print the value in the 1st row (index 0) of column "ID"
        Object cellValue = df.get(0, "ID");  // Expected: 101
        System.out.println("Cell value at (0, \"ID\"): " + cellValue);

        // Retrieve and print the value in the 2nd row (index 1) of column "Name"
        cellValue = df.get(1, "Name");  // Expected: Bob
        System.out.println("Cell value at (1, \"Name\"): " + cellValue);

        // Retrieve and print the value in the 3rd row (index 2) of column "Name"
        cellValue = df.get(2, "Name");  // Expected: Charlie
        System.out.println("Cell value at (2, \"Name\"): " + cellValue);
    }
}
