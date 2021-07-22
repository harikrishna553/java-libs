package com.sample.app;

import java.io.IOException;
import java.util.Date;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class ReferATypeDemo {

	public static void main(String args[]) throws IOException {

		MethodSpec.Builder todaySpecBuilder = MethodSpec.methodBuilder("today");
		todaySpecBuilder.returns(Date.class);
		todaySpecBuilder.addStatement("return new $T()", Date.class);
		todaySpecBuilder.addModifiers(Modifier.PRIVATE, Modifier.STATIC);

		MethodSpec todayMethodSpec = todaySpecBuilder.build();

		MethodSpec.Builder mainMethodSpecBuilder = MethodSpec.methodBuilder("main");
		mainMethodSpecBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
		mainMethodSpecBuilder.returns(void.class);
		mainMethodSpecBuilder.addParameter(String[].class, "args");

		mainMethodSpecBuilder.addCode("\n");
		mainMethodSpecBuilder.addStatement("$T date = today()", Date.class);
		mainMethodSpecBuilder.addStatement("System.out.println(date)");
		mainMethodSpecBuilder.addCode("\n");

		MethodSpec mainMethodSpec = mainMethodSpecBuilder.build();

		String className = "HelloWorld";
		TypeSpec.Builder classSpecBuilder = TypeSpec.classBuilder(className);
		classSpecBuilder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
		classSpecBuilder.addMethod(mainMethodSpec);
		classSpecBuilder.addMethod(todayMethodSpec);
		TypeSpec helloWorldTypeSpec = classSpecBuilder.build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, helloWorldTypeSpec).build();

		javaFile.writeTo(System.out);
	}

}
