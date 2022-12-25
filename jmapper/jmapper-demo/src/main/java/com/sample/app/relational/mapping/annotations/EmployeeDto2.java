package com.sample.app.relational.mapping.annotations;

public class EmployeeDto2 {
	private Integer id;
	private String myName;
	private Integer myAge;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public Integer getMyAge() {
		return myAge;
	}

	public void setMyAge(Integer myAge) {
		this.myAge = myAge;
	}

	@Override
	public String toString() {
		return "EmployeeDto2 [id=" + id + ", myName=" + myName + ", myAge=" + myAge + "]";
	}

}
