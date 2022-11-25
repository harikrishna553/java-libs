package com.sample.app.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import io.micronaut.core.annotation.NonNull;

public class Employee {

	@NonNull
	@Positive
	private Integer id;

	@NotBlank
	private String name;

	@NonNull
	@Positive
	private Integer age;

	public Employee() {
	}

	public Employee(Integer id, String name, Integer age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

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
