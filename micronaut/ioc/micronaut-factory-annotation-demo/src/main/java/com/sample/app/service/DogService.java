package com.sample.app.service;

import com.sample.app.interfaces.Dog;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
public class DogService {

	@Inject
	@Named("myBullDog1")
	private Dog dog1;

	@Inject
	@Named("myBullDog2")
	private Dog dog2;

	@Inject
	@Named("myBullDog3")
	private Dog dog3;

	public void aboutMe() {

		System.out.println(dog1.aboutMe());
		System.out.println(dog2.aboutMe());
		System.out.println(dog3.aboutMe());

	}

}
