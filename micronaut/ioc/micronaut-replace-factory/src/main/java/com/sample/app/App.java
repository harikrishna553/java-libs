package com.sample.app;

import com.sample.app.beans.Book;

import io.micronaut.context.ApplicationContext;
import io.micronaut.inject.qualifiers.Qualifiers;;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {
			Book javaTutorial = applicationContext.getBean(Book.class, Qualifiers.byName("java_tutorial_1"));
			String cTutorial = applicationContext.getBean(String.class, Qualifiers.byName("c_tutorial"));

			System.out.println(javaTutorial);
			System.out.println(cTutorial);

			System.out.println(
					"\n\nAs BooksBeanFactoryV1 is replaced with BooksBeanFactoryV2, getting a bean with name 'java_tutorial_2' will throw NoSuchBeanException");

			try {
				javaTutorial = applicationContext.getBean(Book.class, Qualifiers.byName("java_tutorial_2"));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}