package com.sample.app.model;

public class User {

	private int id;
	private String name;
	private int age;

	private String street;
	private String city;
	private String country;

	public User() {

	}

	public User(int id, String name, int age, String street, String city, String country) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.street = street;
		this.city = city;
		this.country = country;
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + ", street=" + street + ", city=" + city
				+ ", country=" + country + "]";
	}

}
