package com.sample.app;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class StaticImportsDemo {
	public static void main(String args[]) throws IOException {
		ClassName systemClass = ClassName.get("java.lang", "System");
		ClassName collections = ClassName.get("java.util", "Collections");

		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("main");
		methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
		methodBuilder.returns(void.class);
		methodBuilder.addParameter(String[].class, "args");

		methodBuilder.addStatement("out.println($S)", "Hello World!!!");
		MethodSpec mainMethod = methodBuilder.build();

		String className = "HelloWorld";
		TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className);
		classBuilder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
		classBuilder.addMethod(mainMethod);
		TypeSpec helloWorldTypeSpec = classBuilder.build();

		String packageName = "com.sample.app";
		JavaFile.Builder javaFileBuilder = JavaFile.builder(packageName, helloWorldTypeSpec);
		javaFileBuilder.addStaticImport(systemClass, "out");
		javaFileBuilder.addStaticImport(collections, "*");
		JavaFile javaFile = javaFileBuilder.build();

		javaFile.writeTo(System.out);

	}
}
