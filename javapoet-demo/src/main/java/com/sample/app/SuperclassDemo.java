package com.sample.app;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

public class SuperclassDemo {

	public static void main(String args[]) throws IOException {
		TypeSpec myListSpec = TypeSpec.classBuilder("MyList").superclass(ArrayList.class)
				.addSuperinterface(Cloneable.class).addSuperinterface(Serializable.class)
				.addModifiers(Modifier.PUBLIC, Modifier.FINAL).build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, myListSpec).build();

		javaFile.writeTo(System.out);
	}

}