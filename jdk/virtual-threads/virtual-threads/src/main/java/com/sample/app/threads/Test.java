package com.sample.app.threads;

import java.util.HashSet;
import java.util.*;

public class Test {

	static class Employee {
		private int id;
		private String name;

		public Employee() {

		}

		public Employee(int id, String name) {
			this.id = id;
			this.name = name;
		}

		@Override
		public int hashCode() {
			return Objects.hash(id);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Employee other = (Employee) obj;
			return id == other.id;
		}

		@Override
		public String toString() {
			return "Employee [id=" + id + ", name=" + name + "]";
		}
		
		

	}

	public static void main(String[] args) {
		Employee emp1 = new Employee(1, "Krishna");
		Set<Employee> emps = new HashSet<>();
		emps.add(emp1);
		
		System.out.println(emps);
		
		Employee emp2 = new Employee(1, "Ram");
		emps.add(emp2);
		System.out.println(emps);
	}

}
