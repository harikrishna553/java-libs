package com.sample.app.models;

import com.sample.app.interfaces.Dog;

import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
@Named("myBullDog")
public class BullDog implements Dog {

	@Override
	public String aboutMe() {
		return "Bull Dog";
	}

}
