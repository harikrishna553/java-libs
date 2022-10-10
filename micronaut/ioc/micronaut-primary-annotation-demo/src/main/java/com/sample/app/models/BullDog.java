package com.sample.app.models;

import com.sample.app.interfaces.Dog;

import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;

@Singleton
@Primary
public class BullDog implements Dog {

	@Override
	public String aboutMe() {
		return "Bull Dog";
	}

}
