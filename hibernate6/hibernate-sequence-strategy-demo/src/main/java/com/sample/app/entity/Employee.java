package com.sample.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE, 
			generator = "my_employees_seq_generator"
	)
	@SequenceGenerator(
			name = "my_employees_seq_generator", 
			sequenceName = "my_employees_seq",
		    initialValue = 5,
			allocationSize = 10
	)
	private Integer id;

	private String name;

	public Employee(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + "]";
	}

}
