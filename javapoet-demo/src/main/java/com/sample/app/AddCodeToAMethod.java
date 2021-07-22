package com.sample.app;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class AddCodeToAMethod {

	public static void main(String args[]) throws IOException {

		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("main");
		methodBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
		methodBuilder.returns(void.class);
		methodBuilder.addParameter(String[].class, "args");

		methodBuilder.addComment("Print stars triangle");
		methodBuilder.addCode("int i = 0;\n"
				+ "int j = 0;\n"
				+ "\n"
				+ "for(int i = 0; i < 10; i++){\n"
				+ "    for(int j = 0; j < i; j++){\n"
				+ "        System.out.print(\"*\");\n"
				+ "    }\n"
				+ "    System.out.println();\n"
				+ "}");
		
		MethodSpec mainMethodSpec = methodBuilder.build();
		
		
		String className = "HelloWorld";
		TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className);
		classBuilder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
		classBuilder.addMethod(mainMethodSpec);
		TypeSpec helloWorldTypeSpec = classBuilder.build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, helloWorldTypeSpec).build();

		javaFile.writeTo(System.out);
	}

}

