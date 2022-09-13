package com.sample.app.entity;

import java.util.SortedSet;

import org.hibernate.annotations.SortNatural;

import com.sample.app.dto.NickName;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {
	@Id
	@Column(name = "employee_id")
	private int id;

	private String name;

	@ElementCollection
	@SortNatural
	private SortedSet<NickName> nickNames;

	public Employee() {
	}

	public Employee(int id, String name, SortedSet<NickName> nickNames) {
		this.id = id;
		this.name = name;
		this.nickNames = nickNames;
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

	public SortedSet<NickName> getNickNames() {
		return nickNames;
	}

	public void setNickNames(SortedSet<NickName> nickNames) {
		this.nickNames = nickNames;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", nickNames=" + nickNames + "]";
	}

}