package com.sample.app.bean.factories;

import com.sample.app.beans.Book;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Factory
public class BooksBeanFactoryV1 {
	
	@Singleton
	@Named("java_tutorial_1")
	public Book javaBook1() {
		Book book1 = new Book("Complete reference to Java");
		return book1;
	}
	
	@Singleton
	@Named("java_tutorial_2")
	public Book javaBook2() {
		Book book1 = new Book("Java for beginners");
		return book1;
	}
	
	@Singleton
	@Named("c_tutorial")
	public String cBook() {
		return new String("Let us C");
	}

}
