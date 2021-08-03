package com.sample.app;

import java.io.IOException;

import org.modelmapper.ModelMapper;

import com.sample.app.model.Person;
import com.sample.app.model.PersonDto;

public class HelloWorld {

	public static void main(String args[]) throws IOException {
		Person p1 = new Person(1, "Krishna");
		ModelMapper modelMapper = new ModelMapper();

		PersonDto personDto = modelMapper.map(p1, PersonDto.class);
		System.out.println(personDto);

	}

}
