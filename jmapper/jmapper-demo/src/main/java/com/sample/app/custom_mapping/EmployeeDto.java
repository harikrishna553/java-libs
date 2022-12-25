package com.sample.app.custom_mapping;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import com.googlecode.jmapper.annotations.JMap;
import com.googlecode.jmapper.annotations.JMapConversion;

public class EmployeeDto {

	@JMap
	private int id;

	@JMap
	private String name;

	@JMap("dateOfBirth")
	private Long dateOfBirthInMillis;

	@JMapConversion(from = { "dateOfBirth" }, to = { "dateOfBirthInMillis" })
	public Long conversion(LocalDate dateOfBirth) {
		Instant instant = dateOfBirth.atStartOfDay(ZoneId.systemDefault()).toInstant();
		return instant.toEpochMilli();
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

	public Long getDateOfBirthInMillis() {
		return dateOfBirthInMillis;
	}

	public void setDateOfBirthInMillis(Long dateOfBirthInMillis) {
		this.dateOfBirthInMillis = dateOfBirthInMillis;
	}

	@Override
	public String toString() {
		return "EmployeeDto [id=" + id + ", name=" + name + ", dateOfBirthInMillis=" + dateOfBirthInMillis + "]";
	}

}
