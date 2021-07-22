package com.sample.app;

import java.io.IOException;
import java.util.HashMap;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

public class GenericSuperclassDemo {

	public static void main(String args[]) throws IOException {
		TypeSpec myListSpec = TypeSpec.classBuilder("MyMap")
				.superclass(ParameterizedTypeName.get(ClassName.get(HashMap.class), ClassName.get(Integer.class),
						ClassName.get(String.class)))

				.addModifiers(Modifier.PUBLIC, Modifier.FINAL).build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, myListSpec).build();

		javaFile.writeTo(System.out);
	}

}

