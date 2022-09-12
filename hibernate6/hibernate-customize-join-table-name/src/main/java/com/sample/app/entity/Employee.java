package com.sample.app.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {
	@Id
	@Column(name = "employee_id")
	private int id;

	private String name;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
			name = "employee_project_mapping", 
			joinColumns = @JoinColumn(name = "emp_id", referencedColumnName = "employee_id"), 
			inverseJoinColumns = @JoinColumn(name = "pjt_id", referencedColumnName = "project_id"))
	private List<Project> projects = new ArrayList<>();

	public Employee() {
	}

	public Employee(int id, String name) {
		this.id = id;
		this.name = name;
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

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public void addProject(Project project) {
		projects.add(project);
		project.getEmployees().add(this);
	}

	public void removeProject(Project project) {
		projects.remove(project);
		project.getEmployees().remove(this);
	}
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + "]";
	}

}