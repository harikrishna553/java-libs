package com.sample.app;

import com.sample.app.model.Person;

import io.micronaut.context.ApplicationContext;
import io.micronaut.core.beans.BeanIntrospection;
import io.micronaut.core.beans.BeanProperty;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {
			final BeanIntrospection<Person> introspection = BeanIntrospection.getIntrospection(Person.class);
			Person person = introspection.instantiate(1, "Krishna");
			
			System.out.println("id : " + person.readId());
			System.out.println("name : " + person.readName());

			final BeanProperty<Person, String> property = introspection.getRequiredProperty("name", String.class); 
			property.set(person, "Ram"); //
			String name = property.get(person);
		
			System.out.println("\nname : " + name);	
			System.out.println("id : " + person.readId());
			System.out.println("name : " + person.readName());

		}

	}
}