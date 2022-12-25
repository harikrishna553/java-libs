package com.sample.app.relational.mapping.annotations;

import com.googlecode.jmapper.annotations.JMap;

public class Employee {

	@JMap(classes = { EmployeeDto1.class, EmployeeDto2.class })
	private Integer id;

	@JMap(attributes = { "empName", "myName" }, classes = { EmployeeDto1.class, EmployeeDto2.class })
	private String name;

	@JMap(attributes = { "empAge", "myAge" }, classes = { EmployeeDto1.class, EmployeeDto2.class })
	private Integer age;

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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", age=" + age + "]";
	}

}
