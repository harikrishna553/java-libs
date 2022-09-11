package com.sample.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {
	@Id
	private int id;

	private String name;

	@OneToOne
	@JoinColumn(name = "employee_details_id")
	private EmployeeDetails employeeDetails;
	
	public Employee() {}

	public Employee(int id, String name, EmployeeDetails employeeDetails) {
		this.id = id;
		this.name = name;
		this.employeeDetails = employeeDetails;
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

	public EmployeeDetails getEmployeeDetails() {
		return employeeDetails;
	}

	public void setEmployeeDetails(EmployeeDetails employeeDetails) {
		this.employeeDetails = employeeDetails;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", employeeDetails=" + employeeDetails + "]";
	}

}