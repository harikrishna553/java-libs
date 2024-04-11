package com.sample.app.model;

public class Employee {
	private static int totalEmployees;

	private Integer id;
	private String name;
	private int age;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public static int getTotalemployees() {
		return totalEmployees;
	}

	public static int getTotalEmployees() {
		return totalEmployees;
	}

	public static void setTotalEmployees(int totalEmployees) {
		Employee.totalEmployees = totalEmployees;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", age=" + age + "]";
	}

}
