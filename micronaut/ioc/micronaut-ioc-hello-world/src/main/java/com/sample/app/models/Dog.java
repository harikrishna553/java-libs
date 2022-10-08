package com.sample.app.models;

import com.sample.app.interfaces.Animal;

import jakarta.inject.Singleton;

@Singleton
public class Dog implements Animal{

	@Override
	public String color() {
		return "Black";
	}

	@Override
	public String owner() {
		return "Krishna";
	}

}
