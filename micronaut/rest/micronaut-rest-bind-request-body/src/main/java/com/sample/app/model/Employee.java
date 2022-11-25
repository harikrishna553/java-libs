package com.sample.app.model;

import java.util.concurrent.atomic.AtomicInteger;

public final class Employee {

	private static final AtomicInteger UNIQUE_ID_COUNTER = new AtomicInteger(1);

	private Integer id;

	private String name;

	private Integer age;

	private Boolean lock = false;

	public Employee() {
	}

	public Employee(String name, Integer age) {
		this.id = UNIQUE_ID_COUNTER.getAndIncrement();
		this.name = name;
		this.age = age;
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

	public Boolean getLock() {
		return lock;
	}

	public void setLock(Boolean lock) {
		this.lock = lock;
	}

}
