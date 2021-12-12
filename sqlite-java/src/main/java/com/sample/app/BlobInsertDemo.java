package com.sample.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BlobInsertDemo {
	private static final String CONNECTION_URL = "jdbc:sqlite:/Users/Shared/demo.db";
	private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS books (id INTEGER PRIMARY KEY, name TEXT, content BLOB)";
	private static final String INSERT_QUERY = "INSERT INTO books VALUES (?, ?, ?)";
	private static final String SELECT_QUERY = "SELECT id, name, content FROM books";

	private static void insertData(Connection connection, Integer id, String name, String text) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {

			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.setBytes(3, text.getBytes());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void printTableData(Connection connection) {
		System.out.println("Table contents");
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_QUERY)) {

			while (resultSet.next()) {
				Integer id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String text = new String(resultSet.getBytes("content"));

				System.out.println("id : " + id + ", name : " + name + ", text : " + text);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {

		try (Connection connection = DriverManager.getConnection(CONNECTION_URL);
				Statement statement = connection.createStatement()) {
			System.out.println("Connection to SQLite database demo.db is established.");

			statement.execute(CREATE_QUERY);
			System.out.println("\nTable created successfully");

			System.out.println("\nInserting one record to the table\n");
			String content = "Java Design and architecture decisions drew from a variety of languages such as Eiffel, SmallTalk, Objective C, and Cedar/Mesa. Closely observed the problems in the other languages like platform dependent, Pointers complexity, Manual garbage de allocation etc., Java removes the basic problems in other languages.";
			insertData(connection, 1, "Java Tutorial For Beginners", content);

			printTableData(connection);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
