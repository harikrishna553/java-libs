package com.sample.app.service;

public class RemoteService {

	private int succeedAtNthTime;
	private int counter = 0;

	public RemoteService(int succeedAtNthTime) {
		this.succeedAtNthTime = succeedAtNthTime;
	}

	public void accessResource() {
		counter++;

		System.out.println("Executing " + counter + " time");
		if (counter == succeedAtNthTime) {
			System.out.println("Resource responded successfully!!!!!!!!!");
		} else {
			System.err.println("Internal Server error occurred");
			throw new RuntimeException("Internal Server Error");
		}

	}

}
