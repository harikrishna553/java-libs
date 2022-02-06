package com.sample.app;

import java.io.IOException;
import java.util.Arrays;

import org.dozer.DozerBeanMapper;

import com.sample.app.model.Employee;
import com.sample.app.model.Person;

public class MapSpecificFieldDemo {

	public static void main(String[] args) throws IOException {

		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.setMappingFiles(Arrays.asList("map-defined-fields-only.xml"));

		Employee employee = new Employee(1, "Arjun", "Gurram");
		Person person = mapper.map(employee, Person.class);

		System.out.println(employee);
		System.out.println(person);

	}

}
