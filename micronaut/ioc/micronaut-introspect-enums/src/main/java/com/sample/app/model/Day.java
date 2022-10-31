package com.sample.app.model;

import io.micronaut.core.annotation.Introspected;

@Introspected
public enum Day {
	SUNDAY(1), MONDAY(2), TUESDAY(3), WEDNESDAY(4), THURSDAY(5), FRIDAY(6), SATURDAY(7);

	private int dayNumber;

	Day(int dayNumber) {
		this.dayNumber = dayNumber;
	}

	public int getDayNumber() {
		return dayNumber;
	}

}