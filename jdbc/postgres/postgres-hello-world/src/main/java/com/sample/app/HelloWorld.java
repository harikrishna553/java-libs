package com.sample.app;

/* Step 1: Import sql package entities */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloWorld {
	public static void main(String args[]) throws SQLException, ClassNotFoundException {

		/* Open connection to database */
		System.out.println("Connecting to database");
		final String url = "jdbc:postgresql://127.0.0.1:5432/test";
		final String userName = "postgres";
		final String pasword = "postgres";

		try (final Connection conn = DriverManager.getConnection(url, userName, pasword);
				final Statement stmt = conn.createStatement();) {

			/* Query to create employee table */
			String query = "CREATE TABLE employee (id int, name varchar(30), PRIMARY KEY(id))";
			stmt.execute(query);

			/* Insert data to employee table */
			query = "INSERT INTO employee values(1, 'Krishna')";
			stmt.execute(query);
			query = "INSERT INTO employee values(2, 'Arjun')";
			stmt.execute(query);

			query = "SELECT * FROM employee";

			try (final ResultSet rs = stmt.executeQuery(query)) {
				while (rs.next()) {
					int id = rs.getInt("id");
					String name = rs.getString("name");
					System.out.println(id + " " + name);
				}
			}
		}

	}
}