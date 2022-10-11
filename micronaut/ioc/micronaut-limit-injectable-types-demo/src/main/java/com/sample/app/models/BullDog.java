package com.sample.app.models;

import com.sample.app.interfaces.Dog;

import io.micronaut.context.annotation.Bean;
import jakarta.inject.Singleton;

@Singleton
@Bean(typed=Dog.class)
public class BullDog implements Dog {

	@Override
	public String aboutMe() {
		return "Bull Dog";
	}

}
