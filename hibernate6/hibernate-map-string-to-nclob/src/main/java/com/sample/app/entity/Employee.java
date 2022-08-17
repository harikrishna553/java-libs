package com.sample.app.entity;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {
	@Id
	private int id;

	@Nationalized
	private String name;

	@Nationalized
	@Lob
	private String aboutMe;

	public Employee(int id, String name, String aboutMe) {
		super();
		this.id = id;
		this.name = name;
		this.aboutMe = aboutMe;
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

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

}
