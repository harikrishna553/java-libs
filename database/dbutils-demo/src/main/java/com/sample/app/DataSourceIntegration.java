package com.sample.app;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

public class DataSourceIntegration {

	private static DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/sample");
        dataSource.setUsername("root");
        dataSource.setPassword("tiger");
        return dataSource;
    }
	
	private static ResultSetHandler<List<Object[]>> handler = new ResultSetHandler<List<Object[]>>() {

		public List<Object[]> handle(ResultSet rs) throws SQLException {
			// Initialize the list to hold rows
			List<Object[]> rows = new ArrayList<>();

			// Get the metadata of the result set
			ResultSetMetaData meta = rs.getMetaData();
			int columnCount = meta.getColumnCount();

			// Iterate through the result set
			while (rs.next()) {
				// Create an array to hold column values for the current row
				Object[] result = new Object[columnCount];

				// Populate the array with column values
				for (int i = 0; i < columnCount; i++) {
					result[i] = rs.getObject(i + 1);
				}

				// Add the row to the list
				rows.add(result);
			}

			// Return the list of rows
			return rows;
		}
	};
	
	public static void main(String[] args) {
		DataSource datasource = getDataSource();
		
		// Create a QueryRunner
		QueryRunner queryRunner = new QueryRunner(datasource);

		try {

			// Define a SQL query
			String query = "SELECT * FROM employee";

			// Execute the query and retrieve the result
			List<Object[]> result = queryRunner.query(query, handler);

			for (Object[] row : result) {
				for (Object column : row) {
					System.out.print(column + " ");
				}
				System.out.println();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
