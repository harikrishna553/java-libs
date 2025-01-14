package com.sample.app.dataframes;

import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;

import java.util.List;
import java.util.stream.Collectors;

public class DataFrameStreamExample {
    public static void main(String[] args) {
        // Create a sample DataFrame
        String[] names = {"Alice", "Bob", "Charlie", "David"};
        int[] ages = {25, 30, 35, 40};
        DataFrame df = DataFrame.of(
            StringVector.of("Name", names),
            IntVector.of("Age", ages)
        );
        
        // Use the stream() method to filter rows where Age > 30
        List<Tuple> filteredRows = df.stream()
            .filter(row -> row.getInt("Age") > 30)
            .collect(Collectors.toList());
        
        // Print the filtered rows
        System.out.println("Filtered Rows (Age > 30):");
        filteredRows.forEach(row -> 
            System.out.println("Name: " + row.getString("Name") + ", Age: " + row.getInt("Age"))
        );

        // Calculate the sum of ages using stream()
        int totalAge = df.stream()
            .mapToInt(row -> row.getInt("Age"))
            .sum();
        
        System.out.println("Total Age: " + totalAge);
    }
}
