package com.sample.app.util;

import com.sample.app.interfaces.Animal;

import jakarta.inject.Singleton;

@Singleton
public class AnimalUtil {

	private Animal dog;

	public AnimalUtil(Animal dog) {
		this.dog = dog;
	}

	public void aboutMe() {

		System.out.println("Owner : " + dog.owner());
		System.out.println("Color: " + dog.color());

	}

}
