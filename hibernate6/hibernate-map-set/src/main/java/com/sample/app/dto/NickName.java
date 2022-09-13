package com.sample.app.dto;

import jakarta.persistence.Embeddable;

@Embeddable
public class NickName implements Comparable<NickName> {

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
	public int compareTo(NickName nickName) {
		if (nickName == null || nickName.name == null)
			return 0;

		return nickName.name.compareTo(this.name);
	}

}
