package com.sample.app;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class HelloWorld {

	public static void main(String args[]) throws IOException {

		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("main");
		methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
		methodBuilder.returns(void.class);
		methodBuilder.addParameter(String[].class, "args");

		methodBuilder.addStatement("$T.out.println($S)", System.class, "Hello World!!!");
		MethodSpec mainMethod = methodBuilder.build();

		String className = "HelloWorld";
		TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className);
		classBuilder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
		classBuilder.addMethod(mainMethod);
		TypeSpec helloWorldTypeSpec = classBuilder.build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, helloWorldTypeSpec).build();

		javaFile.writeTo(System.out);
	}

}

