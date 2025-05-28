package com.sample.app.dto;

import java.time.LocalDate;

import dev.langchain4j.model.output.structured.Description;

public class Employee {
	// We can add additional details to help LLM have a better understanding.
	@Description("First name of an Employee")
	private String firstName;
	
	@Description("Last name of an Employee")
	private String lastName;
	
	@Description("Employee Birth date, if unable to retrive the valid brith date keep it as null")
	private LocalDate birthDate;
	
	@Description("Employee Address")
	private  Address address;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
