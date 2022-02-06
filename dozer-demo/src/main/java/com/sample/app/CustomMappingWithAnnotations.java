package com.sample.app;

import java.io.IOException;

import org.dozer.DozerBeanMapper;

import com.sample.app.model.Student;
import com.sample.app.model.User;

public class CustomMappingWithAnnotations {

	public static void main(String[] args) throws IOException {

		DozerBeanMapper mapper = new DozerBeanMapper();

		User user1 = new User(1, "Arjun", "Gurram");
		Student student1 = mapper.map(user1, Student.class);
		User user2 = mapper.map(student1, User.class);

		System.out.println(user1);
		System.out.println(student1);
		System.out.println(user2);

	}

}
