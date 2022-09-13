package com.sample.app.dto;

import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class NickName {

	private String name;

	public NickName() {
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NickName other = (NickName) obj;
		return Objects.equals(name, other.name);
	}

}
