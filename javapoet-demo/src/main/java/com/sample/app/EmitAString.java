package com.sample.app;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class EmitAString {
	private static MethodSpec newWelcomeMethod(String methodName, String message) {
		return MethodSpec.methodBuilder(methodName).addModifiers(Modifier.STATIC).returns(String.class)
				.addStatement("return $S", message).build();
	}

	public static void main(String args[]) throws IOException {

		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("main");
		methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
		methodBuilder.returns(void.class);
		methodBuilder.addParameter(String[].class, "args");

		methodBuilder.addStatement("$T.out.println(goodMorning())", System.class);
		methodBuilder.addStatement("$T.out.println(goodAfternoon())", System.class);
		methodBuilder.addStatement("$T.out.println(goodEvening())", System.class);
		MethodSpec mainMethod = methodBuilder.build();
		MethodSpec goodMorningSpec = newWelcomeMethod("goodMorning", "Good Morning!!!!");
		MethodSpec goodAfternoonSpec = newWelcomeMethod("goodAfternoon", "Good Afternoon!!!!");
		MethodSpec goodEveningSpec = newWelcomeMethod("goodEvening", "Good Evening!!!!");

		String className = "Test";
		TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className);
		classBuilder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
		classBuilder.addMethod(mainMethod);
		classBuilder.addMethod(goodMorningSpec);
		classBuilder.addMethod(goodAfternoonSpec);
		classBuilder.addMethod(goodEveningSpec);

		TypeSpec helloWorldTypeSpec = classBuilder.build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, helloWorldTypeSpec).build();

		javaFile.writeTo(System.out);
	}

}
