package com.sample.app.annotations_demo;

import com.googlecode.jmapper.annotations.JGlobalMap;

@JGlobalMap(excluded = {"empAge"})
public class EmployeeDto1 {
	private Integer id;
	private String name;
	private Integer empAge;

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

	public Integer getEmpAge() {
		return empAge;
	}

	public void setEmpAge(Integer empAge) {
		this.empAge = empAge;
	}

	@Override
	public String toString() {
		return "EmployeeDto1 [id=" + id + ", name=" + name + ", empAge=" + empAge + "]";
	}

}
