package com.sample.app;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.sample.app.model.User;
import com.sample.app.model.UserDto;

public class ExplicitMappingDemo {

	public static void main(String args[]) {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.addMappings(new PropertyMap<User, UserDto>() {
			@Override
			protected void configure() {

				map().getAddress().setCity(source.getCity());
				map().getAddress().setCountry(source.getCity());
				map().getAddress().setStreet(source.getStreet());

				map().setUserAge(source.getAge());
			}
		});

		User user = new User(1, "Krishna", 31, "Chowdeswari street", "Bangalore", "India");

		UserDto userDto = modelMapper.map(user, UserDto.class);

		System.out.println(userDto);

	}

}