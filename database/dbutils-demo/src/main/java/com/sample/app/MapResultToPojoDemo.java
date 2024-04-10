package com.sample.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.sample.app.model.Employee;

public class MapResultToPojoDemo {

	private static ResultSetHandler<List<Employee>> empHandler = new BeanListHandler<Employee>(Employee.class);

	public static void main(String[] args) {
		// Database connection parameters
		String url = "jdbc:mysql://localhost:3306/sample";
		String username = "root";
		String password = "tiger";

		// Create a QueryRunner
		QueryRunner queryRunner = new QueryRunner();

		try (Connection connection = DriverManager.getConnection(url, username, password)) {

			// Define a SQL query
			String query = "SELECT * FROM employee";

			// Execute the query and retrieve the result
			List<Employee> emps = queryRunner.query(connection, query, empHandler);

			emps.forEach(System.out::println);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
