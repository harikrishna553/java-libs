package com.sample.app.dto;

import jakarta.persistence.Embeddable;

@Embeddable
public class NickName {

	private String name;
	
	public NickName() {}

	public NickName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "NickName [name=" + name + "]";
	}

}
