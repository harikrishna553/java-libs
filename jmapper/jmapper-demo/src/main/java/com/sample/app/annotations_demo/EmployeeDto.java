package com.sample.app.annotations_demo;

import com.googlecode.jmapper.annotations.JMap;

public class EmployeeDto {

	@JMap
	private Integer id;

	@JMap("name")
	private String empName;

	@JMap("age")
	private Integer empAge;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Integer getEmpAge() {
		return empAge;
	}

	public void setEmpAge(Integer empAge) {
		this.empAge = empAge;
	}

	@Override
	public String toString() {
		return "EmployeeDto [id=" + id + ", empName=" + empName + ", empAge=" + empAge + "]";
	}

}
