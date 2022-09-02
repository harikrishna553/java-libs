package com.sample.app.entity;

import java.io.Serializable;
import java.util.Objects;

public class EmployeePK implements Serializable {
	private Integer empId;
	private Integer deptId;

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

	@Override
	public int hashCode() {
		return Objects.hash(deptId, empId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeePK other = (EmployeePK) obj;
		return Objects.equals(deptId, other.deptId) && Objects.equals(empId, other.empId);
	}

}