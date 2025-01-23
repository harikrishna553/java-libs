package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;

public class GetIndexOfAColumn {
    public static void main(String[] args) {
        // Create sample data
        int[] ids = {1, 2, 3};
        String[] names = {"Alice", "Bob", "Charlie"};
        int[] salaries = {60000, 55000, 70000};

        // Create DataFrame from columns with DataTypes
        DataFrame df = DataFrame.of(
            IntVector.of("id", ids),
            StringVector.of("name", names),
            IntVector.of("salary", salaries)
        );

        // Print the schema and data
        System.out.println("Schema:");
        System.out.println(df.schema());
        System.out.println("\nData:");
        System.out.println(df);

        // Using indexOf to get the index of a column
        int nameIndex = df.indexOf("name");
        int salaryIndex = df.indexOf("salary");

        // Print the indexes of the columns
        System.out.println("\nIndex of 'name' column: " + nameIndex);
        System.out.println("Index of 'salary' column: " + salaryIndex);
        
        // Handling IllegalArgumentException if column name is not found
        try {
            int departmentIndex = df.indexOf("department");
        } catch (IllegalArgumentException e) {
            System.out.println("\nException: Column 'department' does not exist in the DataFrame.");
        }
    }
}
