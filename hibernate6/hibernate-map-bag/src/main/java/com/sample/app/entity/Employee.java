package com.sample.app.entity;

import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.Bag;

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
	private Collection<NickName> nickNames1;

	@ElementCollection
	@Bag
	private List<NickName> nickNames2;

	public Employee() {
	}

	public Employee(int id, String name, Collection<NickName> nickNames1, List<NickName> nickNames2) {
		this.id = id;
		this.name = name;
		this.nickNames1 = nickNames1;
		this.nickNames2 = nickNames2;
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

	public Collection<NickName> getNickNames1() {
		return nickNames1;
	}

	public void setNickNames1(Collection<NickName> nickNames1) {
		this.nickNames1 = nickNames1;
	}

	public List<NickName> getNickNames2() {
		return nickNames2;
	}

	public void setNickNames2(List<NickName> nickNames2) {
		this.nickNames2 = nickNames2;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", nickNames1=" + nickNames1 + ", nickNames2=" + nickNames2
				+ "]";
	}

}