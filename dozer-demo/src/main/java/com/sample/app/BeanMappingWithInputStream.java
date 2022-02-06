package com.sample.app;

import java.io.IOException;
import java.io.InputStream;

import org.dozer.DozerBeanMapper;

import com.sample.app.model.Employee;
import com.sample.app.model.Person;

public class BeanMappingWithInputStream {

	public static void main(String[] args) throws IOException {

		DozerBeanMapper mapper = new DozerBeanMapper();

		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = classloader.getResourceAsStream("employee-person-mapping.xml");

		mapper.addMapping(inputStream);
		
		Employee employee = new Employee(1, "Arjun", "Gurram");
		Person person = mapper.map(employee, Person.class);

		System.out.println(employee);
		System.out.println(person);

	}

}
