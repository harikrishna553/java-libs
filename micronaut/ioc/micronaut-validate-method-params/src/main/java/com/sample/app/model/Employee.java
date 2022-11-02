package com.sample.app.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import jakarta.inject.Singleton;

@Singleton
public class Employee {

	private int id;
	private String name;
	private int age;

	public int getId() {
		return id;
	}

	public void setId(@Positive int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(@NotBlank String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(@Positive int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", age=" + age + "]";
	}

	
}
