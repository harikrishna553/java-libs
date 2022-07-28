package com.sample.app;

import com.sample.app.dto.Animal;

public class SealedClassDemo1 {

	public static void main(String[] args) {
		Animal lion = Animal.lion();
		Animal tiger = Animal.tiger();

		System.out.println(lion.aboutMe());
		System.out.println(tiger.aboutMe());

	}

}
