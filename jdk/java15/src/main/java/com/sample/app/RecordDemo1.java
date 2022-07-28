package com.sample.app;

import com.sample.app.recrods.Employee;

public class RecordDemo1 {
	
	public static void main(String[] args) {
		Employee emp1 = new Employee(1, "Krishna");
		Employee emp2 = new Employee(2, "Krishna");
		Employee emp3 = new Employee(1, "Krishna");

		System.out.println("id = %d".formatted(emp1.id()));
		System.out.println("name = %s".formatted(emp1.name()));

		System.out.println("\nemp1.hashCode() = %d".formatted(emp1.hashCode()));
		System.out.println("emp1.toString() =  %s".formatted(emp1.toString()));

		System.out.println("\nemp2.hashCode() = %d".formatted(emp2.hashCode()));
		System.out.println("emp2.toString() =  %s".formatted(emp2.toString()));

		System.out.println("\nemp3.hashCode() = %d".formatted(emp3.hashCode()));
		System.out.println("emp3.toString() =  %s".formatted(emp3.toString()));

		System.out.println("\nemp1.equals(emp2) = %s".formatted(emp1.equals(emp2)));
		System.out.println("emp1.equals(emp3) = %s".formatted(emp1.equals(emp3)));
	}
	
}
