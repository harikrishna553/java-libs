package com.sample.app.dataframes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import smile.data.DataFrame;
import smile.data.vector.IntVector;
import smile.data.vector.StringVector;
import smile.data.vector.DoubleVector;
import smile.data.vector.BooleanVector;
import smile.data.vector.LongVector;

public class DataFrameExample {

	public static void main(String[] args) {
		// Create some example data
		DataFrame df = DataFrame.of(IntVector.of("age", new int[] { 25, 30, 35, 40 }),
				StringVector.of("name", new String[] { "Alice", "Bob", "Charlie", "David" }),
				DoubleVector.of("salary", new double[] { 50000.00, 60000.00, 70000.00, 80000.00 }),
				BooleanVector.of("isActive", new boolean[] { true, false, true, true }),
				LongVector.of("joinDate",
						new long[] { LocalDateTime.of(2020, 5, 10, 14, 30).toInstant(ZoneOffset.UTC).toEpochMilli(),
								LocalDateTime.of(2019, 3, 12, 9, 45).toInstant(ZoneOffset.UTC).toEpochMilli(),
								LocalDateTime.of(2021, 7, 23, 10, 15).toInstant(ZoneOffset.UTC).toEpochMilli(),
								LocalDateTime.of(2022, 1, 19, 11, 0).toInstant(ZoneOffset.UTC).toEpochMilli() }),
				StringVector.of("balance",
						new String[] { new BigDecimal("1200.50").toString(), new BigDecimal("2200.75").toString(),
								new BigDecimal("3200.00").toString(), new BigDecimal("4200.30").toString() }));

		// Get primitive values, BigDecimal, and LocalDateTime (converted from
		// milliseconds)
		boolean isActive = df.getBoolean(0, "isActive");
		int age = df.getInt(1, "age");
		double salary = df.getDouble(2, "salary");
		String name = df.getString(0, "name");

		// Get balance (BigDecimal using getBigDecimal)
		BigDecimal balance = df.getDecimal(2, "balance");

		// Get joinDate as LocalDateTime (using getLocalDateTime)
		LocalDateTime joinDate = df.getDateTime(1, "joinDate");

		// Display all the retrieved data
		System.out.println("Name: " + name);
		System.out.println("Age: " + age);
		System.out.println("Salary: " + salary);
		System.out.println("Is Active: " + isActive);
		System.out.println("Balance: " + balance);
		System.out.println("Join Date: " + joinDate);
	}
}
