package com.sample.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Properties;

public class TrinoHelloWorld {
	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("io.trino.jdbc.TrinoDriver");

		String url = "jdbc:trino://localhost:8080/demo_tpch/sf1";
		Properties properties = new Properties();
		properties.setProperty("user", "test");

		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM demo_tpch.sf1.customer LIMIT 5")) {

			int columnCount = resultSet.getMetaData().getColumnCount();

			System.out.println("Results:");
			System.out.println("----------------------------------------");

			while (resultSet.next()) {
				for (int i = 1; i <= columnCount; i++) {
					String columnName = resultSet.getMetaData().getColumnLabel(i);
					Object value = resultSet.getObject(i);
					System.out.print(columnName + "=" + value + "; ");
				}
				System.out.println();
			}

			System.out.println("----------------------------------------");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
