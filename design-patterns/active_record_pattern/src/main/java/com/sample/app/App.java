package com.sample.app;

import java.util.List;

import com.sample.app.entity.User;

public class App {
	// Example usage
	public static void main(String[] args) {
		// 1. Create users table
		User.createUserTable();

		// Persist new users
		User newUser1 = new User("Krishna", "krishna@example.com");
		User newUser2 = new User("ptr", "ptr@example.com");
		User newUser3 = new User("ram", "ram@example.com");

		// Save users
		User.save(newUser1);
		User.save(newUser2);
		User.save(newUser3);

		List<User> allUsers = User.all();
		System.out.println("\nAll the users :");
		allUsers.stream().forEach(System.out::println);

		// Retrieve the user by ID 1
		User retrievedUser = User.find(1);
		System.out.println("\nUser details for the id 1 is : " + retrievedUser);

		System.out.println("\nUpdate the user email and username with id 1");
		retrievedUser.setUsername("Joel");
		retrievedUser.setEmail("joel@example.com");
		User.save(retrievedUser);

		retrievedUser = User.find(1);
		System.out.println("\nUser details for the id 1 is : " + retrievedUser);

		System.out.println("\nDelete the user with id 1");
		User.delete(retrievedUser);

		allUsers = User.all();
		System.out.println("All the users :");
		allUsers.stream().forEach(System.out::println);

	}
}
