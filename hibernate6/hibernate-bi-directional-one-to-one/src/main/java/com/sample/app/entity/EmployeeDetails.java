package com.sample.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee_details")
public class EmployeeDetails {

	@Id
	private int id;

	private String departmentName;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "emp_id")
	private Employee emp;

	public EmployeeDetails() {
	}

	public EmployeeDetails(int id, String departmentName, Employee emp) {
		this.id = id;
		this.departmentName = departmentName;
		this.emp = emp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	@Override
	public String toString() {
		return "EmployeeDetails [id=" + id + ", departmentName=" + departmentName + "]";
	}

}
