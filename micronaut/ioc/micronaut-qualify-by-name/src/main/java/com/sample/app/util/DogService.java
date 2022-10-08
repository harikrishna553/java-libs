package com.sample.app.util;

import com.sample.app.interfaces.Dog;

import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
public class DogService {

	private Dog dog1;
	private Dog dog2;

	public DogService(@Named("Bull") Dog dog1, @Named("Poodle") Dog dog2) {
		this.dog1 = dog1;
		this.dog2 = dog2;
	}

	public void aboutMe() {

		System.out.println(dog1.aboutMe());
		System.out.println(dog2.aboutMe());

	}

}
