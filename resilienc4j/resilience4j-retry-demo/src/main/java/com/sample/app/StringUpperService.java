package com.sample.app;

public class StringUpperService {

	private int succeedAtNthTime;
	private int counter = 0;

	public StringUpperService(int succeedAtNthTime) {
		this.succeedAtNthTime = succeedAtNthTime;
	}

	public String toUpper(String str) {
		counter++;

		System.out.println("Executing " + counter + " time");

		if (counter != succeedAtNthTime) {
			System.err.println("Internal Server error occurred");
			throw new RuntimeException("Internal Server Error");
		}
		return str.toUpperCase();
	}

}
