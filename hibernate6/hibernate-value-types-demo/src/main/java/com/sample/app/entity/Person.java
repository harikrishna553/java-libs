package com.sample.app.entity;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "person")
public class Person {

	@Id
	private Integer id;

	@Embedded
	private Name name;

	private Integer age;

	@ElementCollection
	List<Address> addresses;

	public Person(Integer id, Name name, Integer age, List<Address> addresses) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.addresses = addresses;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

}