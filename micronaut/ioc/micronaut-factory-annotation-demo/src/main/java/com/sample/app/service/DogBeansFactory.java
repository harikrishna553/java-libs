package com.sample.app.service;

import com.sample.app.models.BullDog;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Factory
public class DogBeansFactory {

	@Bean
	@Named("myBullDog1")
	public BullDog bullDog1() {
		BullDog bullDog = new BullDog();
		bullDog.setDescription("Bull Dog 1");
		return bullDog;
	}

	@Bean
	@Named("myBullDog2")
	public BullDog bullDog2() {
		BullDog bullDog = new BullDog();
		bullDog.setDescription("Bull Dog 2");
		return bullDog;
	}
	
	@Singleton
	@Named("myBullDog3")
	public BullDog bullDog3() {
		BullDog bullDog = new BullDog();
		bullDog.setDescription("Bull Dog 3");
		return bullDog;
	}

}
