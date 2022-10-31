package com.sample.app;

import com.sample.app.model.Day;

import io.micronaut.context.ApplicationContext;
import io.micronaut.core.beans.BeanIntrospection;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			final BeanIntrospection<Day> introspection = BeanIntrospection.getIntrospection(Day.class);
			Day day = introspection.instantiate("WEDNESDAY");

			System.out.println(day + " -> " + day.getDayNumber());

		}

	}
}