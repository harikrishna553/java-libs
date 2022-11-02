package com.sample.app;

import com.sample.app.model.Employee;

import io.micronaut.context.ApplicationContext;
import io.micronaut.core.beans.BeanWrapper;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			final BeanWrapper<Employee> wrapper = BeanWrapper.getWrapper(new Employee());

			System.out.println("Setting employee instance properties");
			wrapper.setProperty("id", 1);
			wrapper.setProperty("name", "Krishna");
			wrapper.setProperty("age", 34);
			System.out.println(wrapper.getBean());

			System.out.println("\nReading employee instance properties");
			final int id = wrapper.getRequiredProperty("id", int.class);
			final int age = wrapper.getRequiredProperty("age", int.class);
			final String name = wrapper.getRequiredProperty("name", String.class);

			System.out.println("id : " + id);
			System.out.println("age : " + age);
			System.out.println("name : " + name);

		}

	}
}