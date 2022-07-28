package com.sample.app.recrods;

import java.io.Serializable;

public record Employee(int id, String name) implements Serializable{
	
	static {
		System.out.println("Employee record loaded");
	}

	private static int count = 0;

	public Employee {
		count++;
	}

	public static int empCount() {
		return count;
	}
	
	public String empDetails() {
		return this.toString();
	}

}
