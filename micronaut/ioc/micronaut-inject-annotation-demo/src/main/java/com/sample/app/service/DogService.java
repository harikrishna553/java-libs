package com.sample.app.service;

import com.sample.app.interfaces.Dog;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
public class DogService {

	@Inject
	@Named("myBullDog")
	private Dog dog1;

	@Inject
	@Named("myPoodleDog")
	private Dog dog2;

	public void aboutMe() {

		System.out.println(dog1.aboutMe());
		System.out.println(dog2.aboutMe());

	}

}
