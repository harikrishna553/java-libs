package com.sample.app;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class IfElseIfLadderDemo {

	public static void main(String args[]) throws IOException {

		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("main");
		methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
		methodBuilder.returns(void.class);
		methodBuilder.addParameter(String[].class, "args");
		
		methodBuilder.addCode("\n");
		methodBuilder.addStatement("int marks = 45");
		
		methodBuilder.beginControlFlow("if(marks < 35)")
					.addStatement("System.out.println(\"You are failed\")")
					.nextControlFlow("else if( marks < 50)")
					.addStatement("System.out.println(\"You are passed and got third class\")")
					.nextControlFlow("else if( marks < 60)")
					.addStatement("System.out.println(\"You are passed and got second class\")")
					.nextControlFlow("else if( marks < 70)")
					.addStatement("System.out.println(\"You are passed and got first class\")")
					.nextControlFlow("else")
					.addStatement("System.out.println(\"You are passed and got distinction\")")
					.endControlFlow()
					.build();

		MethodSpec mainMethod = methodBuilder.build();

		String className = "Test";
		TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className);
		classBuilder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
		classBuilder.addMethod(mainMethod);
		TypeSpec helloWorldTypeSpec = classBuilder.build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, helloWorldTypeSpec).build();

		javaFile.writeTo(System.out);
	}

}


