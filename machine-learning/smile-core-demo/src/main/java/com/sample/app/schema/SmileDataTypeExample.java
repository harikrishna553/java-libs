package com.sample.app.schema;

import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;

public class SmileDataTypeExample {
    public static void main(String[] args) {
        // Define the fields for an employee schema
        StructField[] fields = new StructField[] {
            new StructField("id", DataTypes.IntegerType),
            new StructField("name", DataTypes.StringType),
            new StructField("salary", DataTypes.DoubleType),
            new StructField("hireDate", DataTypes.DateType)
        };

        // Create the StructType schema
        StructType employeeSchema = new StructType(fields);

        // Print the schema
        System.out.println("Employee Schema:");
        System.out.println(employeeSchema);
    }
}
