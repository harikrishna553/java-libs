package com.sample.app;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

public class NestedClassDemo {

	public static void main(String args[]) throws IOException {

		TypeSpec nestedClass = TypeSpec.classBuilder("NestedClass").addModifiers(Modifier.STATIC).build();
		TypeSpec parentClass = TypeSpec.classBuilder("Test").addType(nestedClass)
				.addModifiers(Modifier.PUBLIC, Modifier.FINAL).build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, parentClass).build();

		javaFile.writeTo(System.out);
	}

}

