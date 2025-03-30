package com.sample.app.tuple;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import smile.data.Tuple;
import smile.data.type.DataTypes;
import smile.data.type.StructField;
import smile.data.type.StructType;

public class TupleCreationFromJDBCResultSet {

	public static void main(String[] args) throws SQLException {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample", "root", "tiger");
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT EmployeeID, FirstName FROM employee")) {
			// Define the schema
			StructField idField = new StructField("EmployeeID", DataTypes.IntegerType);
			StructField nameField = new StructField("FirstName", DataTypes.StringType);
			StructType schema = DataTypes.struct(idField, nameField);

			// Create a Tuple from the ResultSet
			while (rs.next()) {
				Tuple tuple = Tuple.of(rs, schema);
				System.out.println(tuple);
			}
		}

	}

}
