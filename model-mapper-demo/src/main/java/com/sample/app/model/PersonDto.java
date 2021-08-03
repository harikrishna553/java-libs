package com.sample.app.model;

public class PersonDto {
	private int id;
	private String name;
	
	public PersonDto() {
		super();
	}

	public PersonDto(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "PersonDto [id=" + id + ", name=" + name + "]";
	}

}
