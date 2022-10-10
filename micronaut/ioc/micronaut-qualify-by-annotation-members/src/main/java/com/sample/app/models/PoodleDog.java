package com.sample.app.models;

import com.sample.app.annotations.DogType;
import com.sample.app.interfaces.Dog;

import jakarta.inject.Singleton;

@Singleton
@DogType(type = "myPoodleDog")
public class PoodleDog implements Dog {

	@Override
	public String aboutMe() {
		return "I am Poodle Dog";
	}

}
