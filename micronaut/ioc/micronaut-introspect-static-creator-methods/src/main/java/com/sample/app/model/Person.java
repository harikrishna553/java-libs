package com.sample.app.model;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;

@Introspected
public class Person {

	public Integer id;

	public String name;

	private Person(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	@Creator
	public static Person getPerson() {
		System.out.println("Default constructor called");
		return new Person(-1, "no_name");
	}

	@Creator
	public static Person getPerson(Integer id, String name) {
		System.out.println("Constructor with two arguments called");
		return new Person(id, name);
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

}
