package com.sample.app.schema.datatypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;

public class DateTypesExample {
	public static void main(String[] args) {
		// Define fields for DateType, TimeType, and DateTimeType
		StructField dateField = new StructField("birthDate", DataTypes.DateType); // DateType
		StructField timeField = new StructField("wakeUpTime", DataTypes.TimeType); // TimeType
		StructField dateTimeField = new StructField("lastLogin", DataTypes.DateTimeType); // DateTimeType

		// Create a list of fields for the StructType
		List<StructField> fields = new ArrayList<>();
		fields.add(dateField);
		fields.add(timeField);
		fields.add(dateTimeField);

		// Create the schema using DataTypes.struct() to group the fields
		StructType schema = DataTypes.struct(fields);

		// Create data for each of the columns, including DateType, TimeType, and
		// DateTimeType
		List<Tuple> data = new ArrayList<>();
		data.add(Tuple.of(new Object[] { LocalDate.of(1990, 5, 10), // birthDate: DateType
				LocalTime.of(6, 30), // wakeUpTime: TimeType
				LocalDateTime.of(2023, 1, 1, 12, 0) // lastLogin: DateTimeType
		}, schema));

		data.add(Tuple.of(new Object[] { LocalDate.of(1985, 8, 23), // birthDate: DateType
				LocalTime.of(7, 0), // wakeUpTime: TimeType
				LocalDateTime.of(2023, 2, 15, 9, 30) // lastLogin: DateTimeType
		}, schema));

		data.add(Tuple.of(new Object[] { LocalDate.of(2000, 12, 25), // birthDate: DateType
				LocalTime.of(8, 15), // wakeUpTime: TimeType
				LocalDateTime.of(2024, 5, 20, 15, 45) // lastLogin: DateTimeType
		}, schema));

		// Create a DataFrame with the defined schema and data
		DataFrame df = DataFrame.of(data, schema);

		// Print the schema of the DataFrame
		System.out.println("Schema:");
		System.out.println(df.schema());

		// Print the data of the DataFrame
		System.out.println("Data:");
		System.out.println(df);

		// Retrieve and print the first employee's contact information
		Tuple firstRow = df.get(0); // Get the first row
		LocalDate birthDate = firstRow.getDate("birthDate");
		LocalTime wakeUpTime = firstRow.getTime("wakeUpTime");
		LocalDateTime lastLogin = firstRow.getDateTime("lastLogin");

		System.out.println("First Employee's Data:");
		System.out.println("Birth Date: " + birthDate);
		System.out.println("Wake Up Time: " + wakeUpTime);
		System.out.println("Last Login: " + lastLogin);
	}
}
