package com.sample.app;

import com.sample.app.model.Person;

import io.micronaut.context.ApplicationContext;
import io.micronaut.core.beans.BeanIntrospection;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			final BeanIntrospection<Person> introspection = BeanIntrospection.getIntrospection(Person.class);
			Person person = introspection.instantiate(1, "Krishna");

			System.out.println("id : " + person.id);
			System.out.println("name : " + person.name + "\n");

			person = introspection.instantiate();
			System.out.println("id : " + person.id);
			System.out.println("name : " + person.name);

		}

	}
}