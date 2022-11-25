package com.sample.app.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

@Introspected
public final class EmployeeRequestDto {

	@NotBlank
	private String name;

	@NonNull
	@Positive
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
