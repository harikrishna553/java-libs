package com.sample.app;

import com.google.gson.Gson;

public class App {
	static class Employee {
		private int id;
		private String name;

		public Employee(int id, String name) {
			this.id = id;
			this.name = name;
		}

	}

	public static void main(String[] args) {
		Employee emp = new Employee(1, "Krishna");
		Gson gson = new Gson();
		System.out.println(gson.toJson(emp));
		
	}
}
