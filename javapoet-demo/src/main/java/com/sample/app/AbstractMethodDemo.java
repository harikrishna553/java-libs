package com.sample.app;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class AbstractMethodDemo {

	public static void main(String args[]) throws IOException {

		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("connect");
		methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);
		methodBuilder.returns(void.class);
		methodBuilder.addParameter(String[].class, "args");
		MethodSpec mainMethod = methodBuilder.build();

		String className = "Test";
		TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className);
		classBuilder.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);
		classBuilder.addMethod(mainMethod);
		TypeSpec helloWorldTypeSpec = classBuilder.build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, helloWorldTypeSpec).build();

		javaFile.writeTo(System.out);
	}

}

