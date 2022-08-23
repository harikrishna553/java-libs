package com.sample.app.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class EmployeePK implements Serializable {
	private int empId;
	private int deptId;

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	@Override
	public String toString() {
		return "EmployeePK [empId=" + empId + ", deptId=" + deptId + "]";
	}

}