package com.sample.app;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

public class InnerClassDemo1 {

	public static void main(String args[]) throws IOException {

		String className = "Test";
		TypeSpec innerClass = TypeSpec.classBuilder("InnerClass").build();

		ClassName outerName = ClassName.get("com.sample.app", "Test");
		ClassName innerName = outerName.nestedClass("InnerClass");

		TypeSpec parentClass = TypeSpec.classBuilder(className).addType(innerClass)
				.addModifiers(Modifier.PUBLIC, Modifier.FINAL).addField(FieldSpec.builder(innerName, "a").build())
				.build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, parentClass).build();

		javaFile.writeTo(System.out);
	}

}
