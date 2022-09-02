package com.sample.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
@IdClass(EmployeePK.class)
public class Employee {

	@Id
	private Integer empId;

	@Id
	private Integer deptId;

	private String name;

	public Employee(Integer empId, Integer deptId, String name) {
		this.empId = empId;
		this.deptId = deptId;
		this.name = name;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", deptId=" + deptId + ", name=" + name + "]";
	}

}