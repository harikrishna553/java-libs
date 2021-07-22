package com.sample.app;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class TryCatchFinallyDemo {

	public static void main(String args[]) throws IOException {

		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("main");
		methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
		methodBuilder.returns(void.class);
		methodBuilder.addParameter(String[].class, "args");
		
		methodBuilder.addCode("\n");
		
		methodBuilder.beginControlFlow("try")
					.addStatement("int i = 10 / 0")
					.nextControlFlow("catch($T e)", ArithmeticException.class)
					.addStatement("System.out.println(\"Error occuured\")")
					.addStatement("e.printStackTrace()")
					.nextControlFlow("catch($T e)", Exception.class)
					.addStatement("System.out.println(\"Error occuured\")")
					.addStatement("e.printStackTrace()")
					.nextControlFlow("finally")
					.addStatement("System.out.println(\"I am in finally block\")")
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


