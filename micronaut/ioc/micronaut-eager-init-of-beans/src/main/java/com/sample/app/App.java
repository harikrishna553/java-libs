package com.sample.app;

import io.micronaut.runtime.Micronaut;

public class App {
	public static void main(String[] args) {
		 Micronaut.build(args)
         .eagerInitSingletons(true) 
         .mainClass(App.class)
         .start();
	}
}