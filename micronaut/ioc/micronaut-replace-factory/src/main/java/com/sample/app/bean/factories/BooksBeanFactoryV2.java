package com.sample.app.bean.factories;

import com.sample.app.beans.Book;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Replaces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Factory
@Replaces(factory = BooksBeanFactoryV1.class)
public class BooksBeanFactoryV2 {

	@Singleton
	@Named("java_tutorial_1")
	public Book javaBook() {
		Book book1 = new Book("Programming for beginners (https://self-learning-java-tutorial.blogspot.com)");
		return book1;
	}
}
