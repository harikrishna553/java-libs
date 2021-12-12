package com.sample.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
	private static final String CONNECTION_URL = "jdbc:sqlite:/Users/Shared/demo.db";
	private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS employees (id INTEGER PRIMARY KEY, name TEXT, city TEXT, age INTEGER)";

	public static void main(String[] args) {

		try (Connection connection = DriverManager.getConnection(CONNECTION_URL);
				Statement statement = connection.createStatement()) {
			System.out.println("Connection to SQLite database demo.db is established.");
			statement.execute(CREATE_QUERY);

			System.out.println("Table created successfully");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
