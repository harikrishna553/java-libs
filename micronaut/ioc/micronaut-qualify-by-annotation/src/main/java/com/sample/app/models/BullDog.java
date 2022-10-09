package com.sample.app.models;

import com.sample.app.interfaces.Dog;

import jakarta.inject.Singleton;

@Singleton
public class BullDog implements Dog {

	@Override
	public String aboutMe() {
		return "I am Bull Dog";
	}

}
