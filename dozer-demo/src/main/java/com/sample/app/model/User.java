package com.sample.app.model;

public class User {

	private Integer id;
	private String fName;
	private String lName;

	public User() {
	}

	public User(Integer id, String fName, String lName) {
		this.id = id;
		this.fName = fName;
		this.lName = lName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fName=" + fName + ", lName=" + lName + "]";
	}

}
