package com.sample.app.dto;

import dev.langchain4j.model.output.structured.Description;

//you can add an optional description to help an LLM have a better understanding
@Description("Specify an Employee Address")
class Address {
	private String street;
	private Integer streetNumber;
	private String city;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(Integer streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}