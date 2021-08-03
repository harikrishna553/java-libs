package com.sample.app.model;

public class UserDto {
	private int id;
	private String name;
	private int userAge;

	private Address address;

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

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", userAge=" + userAge + ", address=" + address + "]";
	}

}
