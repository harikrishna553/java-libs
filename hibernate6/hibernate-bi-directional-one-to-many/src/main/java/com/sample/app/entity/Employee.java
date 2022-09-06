package com.sample.app.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {
	@Id
	private int id;

	private String firstName;

	private String lastName;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<BankAccount> bankAccounts = new HashSet<>();

	public void addBankAccount(BankAccount bankAccount) {
		bankAccounts.add(bankAccount);
		bankAccount.setEmployee(this);
	}

	public void removeBankAccount(BankAccount bankAccount) {
		bankAccounts.remove(bankAccount);
		bankAccount.setEmployee(this);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Set<BankAccount> getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(Set<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", bankAccounts="
				+ bankAccounts + "]";
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
		Employee other = (Employee) obj;
		return id == other.id;
	}

}