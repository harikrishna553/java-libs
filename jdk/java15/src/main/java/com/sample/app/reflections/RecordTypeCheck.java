package com.sample.app.reflections;

public class RecordTypeCheck {

	public static void main(String[] args) {
		boolean isRecord = Point.class.isRecord();

		System.out.println("Is Point record type? " + isRecord);
	}

}
