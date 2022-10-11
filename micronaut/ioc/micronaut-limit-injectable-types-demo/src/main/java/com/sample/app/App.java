package com.sample.app;

import com.sample.app.interfaces.Dog;
import com.sample.app.models.BullDog;

import io.micronaut.context.ApplicationContext;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			System.out.println("Trying to get the bean of BullDog");
			try {
				BullDog bullDog = applicationContext.getBean(BullDog.class);
				System.out.println(bullDog.aboutMe());
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("\n\nTrying to get the bean of Dog");
			try {
				Dog dog = applicationContext.getBean(Dog.class);
				System.out.println(dog.aboutMe());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
