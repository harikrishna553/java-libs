package com.sample.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.commons.dbutils.AsyncQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RunQueriesAsynchronously {

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

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// Database connection parameters
		String url = "jdbc:mysql://localhost:3306/sample";
		String username = "root";
		String password = "tiger";

		// Create a AsyncQueryRunner
		ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		AsyncQueryRunner queryRunner = new AsyncQueryRunner(executorService);

		try (Connection connection = DriverManager.getConnection(url, username, password)) {

			// Define a SQL query
			String query = "SELECT * FROM employee";

			// Execute the query and retrieve the result
			Future<List<Object[]>> resultFuture = queryRunner.query(connection, query, handler);

			List<Object[]> result = resultFuture.get();

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

