package com.sample.app.entity;

import com.sample.app.converters.GenderConverter;
import com.sample.app.enums.Gender;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "person")
public class Person {

	@Id
	private Integer id;

	private String name;

	@Convert(converter = GenderConverter.class)
	private Gender gender;

	public Person(Integer id, String name, Gender gender) {
		this.id = id;
		this.name = name;
		this.gender = gender;
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

}
