package com.sample.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteData {
	private static final String CONNECTION_URL = "jdbc:sqlite:/Users/Shared/demo.db";
	private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS employees (id INTEGER PRIMARY KEY, name TEXT, city TEXT, age INTEGER)";
	private static final String INSERT_QUERY = "INSERT INTO employees VALUES (?, ?, ?, ?)";
	private static final String SELECT_QUERY = "SELECT id, name, city, age FROM employees";
	private static final String DELETE_QUERY = "DELETE FROM employees WHERE id = ?";

	private static void insertData(Connection connection, Integer id, String name, String city, Integer age) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {

			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, city);
			preparedStatement.setInt(4, age);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void printTableData(Connection connection) {
		System.out.println("\nAll the records from employee table\n");
		try (Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_QUERY)) {

			while (resultSet.next()) {
				Integer id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String city = resultSet.getString("city");
				Integer age = resultSet.getInt("age");

				System.out.println("id : " + id + ", name : " + name + ", city : " + city + ", age " + age);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void deleteRow(Connection connection, Integer recordId) {
		try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
			preparedStatement.setInt(1, recordId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		try (Connection connection = DriverManager.getConnection(CONNECTION_URL);
				Statement statement = connection.createStatement()) {
			System.out.println("Connection to SQLite database demo.db is established.\n");

			statement.execute(CREATE_QUERY);
			System.out.println("Table created successfully\n");

			insertData(connection, 1, "Krishna", "Gurram", 32);
			insertData(connection, 2, "PTR", "D", 33);
			insertData(connection, 3, "Ram", "Majety", 35);
			insertData(connection, 4, "Joel", "Chelli", 29);

			System.out.println("Data inserted successfully\n");

			printTableData(connection);
			
			System.out.println("\nDeleting the record with id 2");
			deleteRow(connection, 2);
			
			printTableData(connection);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
