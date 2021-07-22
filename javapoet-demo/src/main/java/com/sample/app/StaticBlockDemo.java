package com.sample.app;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class StaticBlockDemo {

	public static void main(String args[]) throws IOException {

		MethodSpec mainMethod = MethodSpec.methodBuilder("main").addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.returns(void.class).addParameter(String[].class, "args")
				.addStatement("$T.out.println($S)", System.class, "Hello World!!!").build();

		TypeSpec classSpec = TypeSpec.classBuilder("Test")
				.addStaticBlock(CodeBlock.builder().add("$T.out.println($S);\n", System.class, "In staticblock").build())
				.addModifiers(Modifier.PUBLIC, Modifier.FINAL).addMethod(mainMethod).build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, classSpec).build();

		javaFile.writeTo(System.out);
	}

}
