package com.sample.app.model;

import io.micronaut.core.annotation.AccessorsStyle;
import io.micronaut.core.annotation.Introspected;

@Introspected
@AccessorsStyle(readPrefixes = { "read" }, writePrefixes = { "write" })
public class Person {

	private Integer id;

	private String name;

	public Person(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer readId() {
		return id;
	}

	public void writeId(Integer id) {
		this.id = id;
	}

	public String readName() {
		return name;
	}

	public void writeName(String name) {
		this.name = name;
	}

}
