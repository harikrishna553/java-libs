package com.sample.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;

@Entity
@Table(name = "persons")
public class Person {
	
	@Id
	@GeneratedValue(
		strategy = GenerationType.TABLE,
		generator = "my-table-generator"
	)
	@TableGenerator(
		name =  "my-table-generator",
		table = "my_table_identifier",
		pkColumnName = "table_name",
		valueColumnName = "record_id",
		allocationSize = 5
	)
	private Integer id;

	private String name;

	public Person(String name) {
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
		return "Person [id=" + id + ", name=" + name + "]";
	}

}
