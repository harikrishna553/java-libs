package com.sample.app.dataframes;

import java.util.stream.Stream;

import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.measure.NominalScale;
import smile.data.measure.OrdinalScale;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;

public class ScaleExample {
	public static void main(String[] args) {
		// Define NominalScale for Gender (e.g., 0 = Male, 1 = Female)
		NominalScale genderScale = new NominalScale("Male", "Female");

		// Define OrdinalScale for Rating (e.g., 0 = Poor, 1 = Average, 2 = Good, 3 =
		// Excellent)
		OrdinalScale ratingScale = new OrdinalScale(new int[] { 2, 3, 5, 7 },
				new String[] { "Poor", "Average", "Good", "Excellent" });

		// Create the schema manually (column definitions with measures)
		StructType schema = DataTypes.struct(new StructField("userId", DataTypes.IntegerType),
				new StructField("gender", DataTypes.IntegerType, genderScale),
				new StructField("rating", DataTypes.IntegerType, ratingScale));

		// Create the DataFrame using a Stream of Tuples
		Stream<Tuple> dataStream = Stream.of(Tuple.of(new int[] { 1, 0, 3 }, schema),
				Tuple.of(new int[] { 2, 1, 2 }, schema), Tuple.of(new int[] { 3, 1, 5 }, schema),
				Tuple.of(new int[] { 4, 0, 7 }, schema));

		DataFrame dataFrame = DataFrame.of(dataStream, schema);

		// Iterate over the rows and print the scaled values
		for (int i = 0; i < dataFrame.nrow(); i++) {
			String userId = String.valueOf(dataFrame.getInt(i, "userId"));
			String gender = dataFrame.getScale(i, "gender"); // Scaled value using the NominalScale
			String rating = dataFrame.getScale(i, "rating"); // Scaled value using the OrdinalScale

			System.out.printf("User ID: %s, Gender: %s, Rating: %s%n", userId, gender, rating);
		}
	}
}
