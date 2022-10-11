package com.sample.app.models;

import com.sample.app.interfaces.Dog;

public class BullDog implements Dog {

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String aboutMe() {
		return description;
	}

}
