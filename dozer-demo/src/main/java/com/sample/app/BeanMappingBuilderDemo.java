package com.sample.app;

import java.io.IOException;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import com.sample.app.model.Employee;
import com.sample.app.model.Person;

public class BeanMappingBuilderDemo {

	public static void main(String[] args) throws IOException {

		BeanMappingBuilder beanMappingBuilder = new BeanMappingBuilder() {

			@Override
			protected void configure() {
				mapping(Employee.class, Person.class)
				.fields("fName", "firstName")
				.fields("lName", "lastName");

			}
		};

		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.addMapping(beanMappingBuilder);

		Employee employee = new Employee(1, "Arjun", "Gurram");
		Person person = mapper.map(employee, Person.class);

		System.out.println(employee);
		System.out.println(person);

	}

}
