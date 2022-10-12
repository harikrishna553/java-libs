package com.sample.app.model;

import java.util.concurrent.atomic.AtomicInteger;

public class DemoBean {
	private static AtomicInteger counter = new AtomicInteger(1);

	private int id;

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DemoBean() {
		System.out.println("DemoBean default constructor called for ");
	}

	public DemoBean(String description) {
		this.description = description;
		this.id = counter.getAndIncrement();
		System.out.println("DemoBean constructor called for " + description);
	}

	@Override
	public String toString() {
		int identityHashCode = System.identityHashCode(this);

		return "DemoBean [id=" + id + ", description=" + description + ", identityHashCode= " + identityHashCode + "]";
	}

}
