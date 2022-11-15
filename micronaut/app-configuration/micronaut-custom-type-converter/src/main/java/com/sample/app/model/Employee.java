package com.sample.app.model;

public class Employee {

	private Integer id;
	private String name;
	private Integer age;
	private String country;

	public Employee() {
	}

	public Employee(Integer id, String name, Integer age, String country) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.country = country;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", age=" + age + ", country=" + country + "]";
	}

	public static Employee toEmployee(final String str) {
		if (str == null) {
			throw new IllegalArgumentException("Can't get employee instance from null string");
		}
		String[] tokens = str.split(",");
		if (tokens.length != 4) {
			throw new IllegalArgumentException("Information missig to construct employee object");
		}

		try {
			Integer id = Integer.valueOf(tokens[0]);
			String name = tokens[1];
			Integer age = Integer.valueOf(tokens[2]);
			String country = tokens[3];

			return new Employee(id, name, age, country);

		} catch (Exception e) {
			throw e;
		}
	}

}
