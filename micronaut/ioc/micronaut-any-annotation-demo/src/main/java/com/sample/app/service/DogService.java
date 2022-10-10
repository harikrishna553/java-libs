package com.sample.app.service;

import com.sample.app.interfaces.Dog;

import io.micronaut.context.annotation.Any;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class DogService {

	@Inject
	@Any
	private Dog dog1;

	public void aboutMe() {

		System.out.println(dog1.aboutMe());

	}

}
