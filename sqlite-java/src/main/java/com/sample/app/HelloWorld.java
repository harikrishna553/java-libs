package com.sample.app;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HelloWorld {
	private static final String CONNECTION_URL = "jdbc:sqlite:/Users/Shared/demo.db";

	public static void main(String[] args) {
		try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
			System.out.println("Connection to SQLite database demo.db is established.");

			DatabaseMetaData databaseMetaData = connection.getMetaData();

			System.out.println("Database Product name : " + databaseMetaData.getDatabaseProductName());

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
