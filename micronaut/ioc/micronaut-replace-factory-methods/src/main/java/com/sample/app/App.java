package com.sample.app;

import com.sample.app.beans.Book;

import io.micronaut.context.ApplicationContext;
import io.micronaut.inject.qualifiers.Qualifiers;;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {
			Book javaTutorial1 = applicationContext.getBean(Book.class, Qualifiers.byName("java_tutorial_1"));
			Book javaTutorial2 = applicationContext.getBean(Book.class, Qualifiers.byName("java_tutorial_2"));
			String cTutorial = applicationContext.getBean(String.class, Qualifiers.byName("c_tutorial"));

			System.out.println(javaTutorial1);
			System.out.println(javaTutorial2);
			System.out.println(cTutorial);

		}
	}
}