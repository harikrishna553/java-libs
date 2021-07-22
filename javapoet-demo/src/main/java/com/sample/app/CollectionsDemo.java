package com.sample.app;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public class CollectionsDemo {

	public static void main(String args[]) throws IOException {

		MethodSpec.Builder mainMethodSpecBuilder = MethodSpec.methodBuilder("main");
		mainMethodSpecBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
		mainMethodSpecBuilder.returns(void.class);
		mainMethodSpecBuilder.addParameter(String[].class, "args");

		mainMethodSpecBuilder.addCode("\n");
		ClassName StringClass = ClassName.get("java.lang", "String");
		ClassName list = ClassName.get("java.util", "List");
		ClassName arrayList = ClassName.get("java.util", "ArrayList");
		TypeName listOfStrings = ParameterizedTypeName.get(list, StringClass);

		mainMethodSpecBuilder.addStatement("$T result = new $T<>()", listOfStrings, arrayList);

		mainMethodSpecBuilder.addCode("\n");

		MethodSpec mainMethodSpec = mainMethodSpecBuilder.build();

		String className = "HelloWorld";
		TypeSpec.Builder classSpecBuilder = TypeSpec.classBuilder(className);
		classSpecBuilder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
		classSpecBuilder.addMethod(mainMethodSpec);
		TypeSpec helloWorldTypeSpec = classSpecBuilder.build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, helloWorldTypeSpec).build();

		javaFile.writeTo(System.out);
	}
}