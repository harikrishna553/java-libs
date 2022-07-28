package com.sample.app.dto;

public abstract sealed class Animal {

	public abstract String aboutMe();
	
	public static Animal tiger() {
		return new Tiger();
	}
	
	public static Animal lion() {
		return new Lion();
	}
}

final class Tiger extends Animal {

	@Override
	public String aboutMe() {
		return "I am Tiger";
	}

}

final class Lion extends Animal {

	@Override
	public String aboutMe() {
		return "I am Lion";
	}

}

