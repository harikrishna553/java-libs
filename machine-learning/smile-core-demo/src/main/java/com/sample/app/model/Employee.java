package com.sample.app.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Employee {
	private int id;
	private String name;
	private boolean isActive;
	private LocalDateTime hireDate;
	private BigDecimal salary;

	// Constructor
	public Employee(int id, String name, boolean isActive, LocalDateTime hireDate, BigDecimal salary) {
		this.id = id;
		this.name = name;
		this.isActive = isActive;
		this.hireDate = hireDate;
		this.salary = salary;
	}

	// Getters and Setters
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public LocalDateTime getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDateTime hireDate) {
		this.hireDate = hireDate;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	// Optional: Override toString() for better representation
	@Override
	public String toString() {
		return "Employee{id=" + id + ", name='" + name + "', isActive=" + isActive + ", hireDate=" + hireDate
				+ ", salary=" + salary + "}";
	}
}
