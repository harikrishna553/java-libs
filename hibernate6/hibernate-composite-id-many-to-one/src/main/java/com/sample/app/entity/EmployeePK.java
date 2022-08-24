package com.sample.app.entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Embeddable
public class EmployeePK implements Serializable {
	private int empId;
	private int deptId;

	@ManyToOne(fetch = FetchType.LAZY)
	private Project project;

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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "EmployeePK [empId=" + empId + ", deptId=" + deptId + "]";
	}

}