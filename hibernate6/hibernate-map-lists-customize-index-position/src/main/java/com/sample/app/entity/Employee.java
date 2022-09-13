package com.sample.app.entity;

import java.util.List;

import org.hibernate.annotations.ListIndexBase;

import com.sample.app.dto.NickName;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {
	@Id
	@Column(name = "employee_id")
	private int id;

	private String name;

	@ElementCollection
	@OrderColumn(name = "nick_names_sequence")
	@ListIndexBase(13)
	private List<NickName> nickNames;

	public Employee() {
	}

	public Employee(int id, String name, List<NickName> nickNames) {
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

	public List<NickName> getNickNames() {
		return nickNames;
	}

	public void setNickNames(List<NickName> nickNames) {
		this.nickNames = nickNames;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", nickNames=" + nickNames + "]";
	}

}