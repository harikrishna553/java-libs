package com.sample.app.xml_config_demo;

public class EmployeeDto1 {
	private Integer id;
	private String name;
	private Integer empAge;
	private String empHobbies;

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

	public String getEmpHobbies() {
		return empHobbies;
	}

	public void setEmpHobbies(String empHobbies) {
		this.empHobbies = empHobbies;
	}

	@Override
	public String toString() {
		return "EmployeeDto1 [id=" + id + ", name=" + name + ", empAge=" + empAge + ", empHobbies=" + empHobbies + "]";
	}

}
