package com.sample.app.model;

public final class Employee {
	private final Integer id;
	private final String name;
	private final Integer age;

	public Employee(Integer id, String name, Integer age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getAge() {
		return age;
	}

}
