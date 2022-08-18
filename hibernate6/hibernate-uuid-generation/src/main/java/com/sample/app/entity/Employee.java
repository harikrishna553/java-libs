package com.sample.app.entity;

import java.util.Locale;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@UuidGenerator
	private UUID id;

	/*
	 * @Id
	 * @GeneratedValue
	 * @UuidGenerator private UUID id;
	 */

	private String name;

	private Locale userLocale;

	public Employee(String name, Locale userLocale) {
		this.name = name;
		this.userLocale = userLocale;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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
