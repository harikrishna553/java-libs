package com.sample.app.models;

import com.sample.app.interfaces.Dog;

import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
@Named("myPoodleDog")
public class PoodleDog implements Dog {

	@Override
	public String aboutMe() {
		return "Poodle Dog";
	}

}
