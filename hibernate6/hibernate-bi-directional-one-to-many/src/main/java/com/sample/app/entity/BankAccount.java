package com.sample.app.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bank_account_details")
public class BankAccount {

	@Id
	private int id;

	private String accountNumber;

	private String branch;

	private String ifscCode;

	private String address;

	@ManyToOne
	@JoinColumn(name = "employee_id", foreignKey = @ForeignKey(name = "bank_acc_emp_id_fk"))
	private Employee employee;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "BankAccount [id=" + id + ", accountNumber=" + accountNumber + ", branch=" + branch + ", ifscCode="
				+ ifscCode + ", address=" + address + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankAccount other = (BankAccount) obj;
		return id == other.id;
	}

}