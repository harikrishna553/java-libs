package com.sample.app.relational.mapping.apis;

public class EmployeeDto1 {
	private Integer id;
	private String empName;
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
		return "EmployeeDto1 [id=" + id + ", empName=" + empName + ", empAge=" + empAge + "]";
	}

}
