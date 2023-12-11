package com.sample.app.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.sample.app.util.DBConnectionUtil;

// Active Record class representing a "User" table in the database
public class User {
	private static boolean USER_TABLE_CREATED = false;
	private static AtomicInteger ID_COUNTER = new AtomicInteger(0);

	private Integer id;
	private String username;
	private String email;

	// Constructor
	public User(int id, String username, String email) {
		this.id = id;
		this.username = username;
		this.email = email;
	}

	public User(String username, String email) {
		this.username = username;
		this.email = email;
	}

	// Getters and setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// Save method to insert or update the record in the database
	public static User save(User user) {
		Integer newId = user.id;
		try (Connection connection = DBConnectionUtil.connection()) {
			if (user.id == null) {
				// Insert new record
				newId = ID_COUNTER.incrementAndGet();
				String insertQuery = "INSERT INTO users (id, username, email) VALUES (?, ?, ?)";
				try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
					preparedStatement.setInt(1, newId);
					preparedStatement.setString(2, user.getUsername());
					preparedStatement.setString(3, user.getEmail());
					preparedStatement.executeUpdate();
				}
			} else {
				// Update existing record
				String updateQuery = "UPDATE users SET username = ?, email = ? WHERE id = ?";
				try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
					preparedStatement.setString(1, user.getUsername());
					preparedStatement.setString(2, user.getEmail());
					preparedStatement.setInt(3, user.getId());
					preparedStatement.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return find(newId);
	}

	// Find method to retrieve a record by ID from the database
	public static User find(int id) {
		try (Connection connection = DBConnectionUtil.connection()) {
			String selectQuery = "SELECT * FROM users WHERE id = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
				preparedStatement.setInt(1, id);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						// Map database columns to object properties
						String username = resultSet.getString("username");
						String email = resultSet.getString("email");
						// Return a new User object
						return new User(id, username, email);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Delete method to remove the record from the database
	public static void delete(User user) {
		try (Connection connection = DBConnectionUtil.connection()) {
			String deleteQuery = "DELETE FROM users WHERE id = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
				preparedStatement.setInt(1, user.getId());
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<User> all() {
		List<User> users = new ArrayList<>();
		try (Connection connection = DBConnectionUtil.connection()) {
			String deleteQuery = "SELECT * FROM users";
			try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {
						// Map database columns to object properties
						String username = resultSet.getString("username");
						String email = resultSet.getString("email");
						Integer id = resultSet.getInt("id");
						// Return a new User object
						users.add(new User(id, username, email));
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	public static void createUserTable() {
		if (USER_TABLE_CREATED) {
			return;
		}

		try (Connection connection = DBConnectionUtil.connection()) {
			String createTable = "CREATE TABLE users (id int, username varchar(50), email varchar(100), PRIMARY KEY(id))";
			try (PreparedStatement preparedStatement = connection.prepareStatement(createTable)) {
				preparedStatement.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + "]";
	}

}
