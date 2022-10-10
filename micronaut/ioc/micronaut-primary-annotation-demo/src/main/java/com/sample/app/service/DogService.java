package com.sample.app.service;

import com.sample.app.interfaces.Dog;

import jakarta.inject.Singleton;

@Singleton
public class DogService {

	private Dog dog1;

	public DogService(Dog dog1) {
		this.dog1 = dog1;
	}

	public void aboutMe() {

		System.out.println(dog1.aboutMe());

	}

}
