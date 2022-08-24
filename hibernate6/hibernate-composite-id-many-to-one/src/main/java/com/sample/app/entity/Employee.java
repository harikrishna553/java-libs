package com.sample.app.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {
	@EmbeddedId
	private EmployeePK empPK;
	
	private String firstName;
	private String lastName;

	public EmployeePK getEmpPK() {
		return empPK;
	}

	public void setEmpPK(EmployeePK empPK) {
		this.empPK = empPK;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Employee [empPK=" + empPK + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
	

}