package com.sample.app.entity;

import java.util.Locale;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {
	@Id
	private int id;

	private String name;

	private Locale userLocale;

	public Employee(int id, String name, Locale userLocale) {
		this.id = id;
		this.name = name;
		this.userLocale = userLocale;
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

	public Locale getUserLocale() {
		return userLocale;
	}

	public void setUserLocale(Locale userLocale) {
		this.userLocale = userLocale;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", userLocale=" + userLocale + "]";
	}

}
