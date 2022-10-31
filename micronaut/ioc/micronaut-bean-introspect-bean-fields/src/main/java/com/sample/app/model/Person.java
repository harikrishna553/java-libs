package com.sample.app.model;

import io.micronaut.core.annotation.Introspected;

@Introspected(accessKind = Introspected.AccessKind.FIELD)
public class Person {

	public Integer id;

	public String name;

	public Person(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

}
