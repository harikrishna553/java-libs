package com.sample.app;

import io.micronaut.context.ApplicationContext;
import io.micronaut.inject.qualifiers.Qualifiers;;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {
			int[] primes = applicationContext.getBean(int[].class,
					Qualifiers.byName("primes"));
			
			for(int p: primes) {
				System.out.println(p);
			}
		}
	}
}