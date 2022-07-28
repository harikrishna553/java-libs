package com.sample.app;

import com.sample.app.recrods.Employee;

public class RecordDemo {

	public static void main(String[] args) {
		Employee emp1 = new Employee(1, "Krishna");

		System.out.println("id = %d".formatted(emp1.id()));
		System.out.println("name = %s".formatted(emp1.name()));
	}

}
