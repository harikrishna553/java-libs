package com.sample.app;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

public class InnerClassDemo {

	public static void main(String args[]) throws IOException {

		String className = "Test";
		TypeSpec innerClass = TypeSpec.classBuilder("InnerClass").build();
		TypeSpec parentClass = TypeSpec.classBuilder(className).addType(innerClass)
				.addModifiers(Modifier.PUBLIC, Modifier.FINAL).build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, parentClass).build();

		javaFile.writeTo(System.out);
	}

}
