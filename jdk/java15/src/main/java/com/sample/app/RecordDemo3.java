package com.sample.app;

import com.sample.app.recrods.Employee;

public class RecordDemo3 {
	
	public static void main(String[] args) {
		Employee emp1 = new Employee(1, "Krishna");
		Employee emp2 = new Employee(2, "Ram");
		
		System.out.println("Total employees : "+ Employee.empCount());
	}

}
