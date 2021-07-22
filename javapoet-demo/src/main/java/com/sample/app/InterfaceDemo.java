package com.sample.app;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public class InterfaceDemo {

	public static void main(String args[]) throws IOException {
		TypeSpec helloWorld = TypeSpec.interfaceBuilder("Circle")
			    .addModifiers(Modifier.PUBLIC)
			    .addField(FieldSpec.builder(float.class, "PI")
			        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
			        .initializer(CodeBlock.builder().add("3.14f").build())
			        .build())
			    .addMethod(MethodSpec.methodBuilder("areaOfCircle")
			    		.returns(TypeName.FLOAT)
			        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
			        .build())
			    .build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, helloWorld).build();

		javaFile.writeTo(System.out);
	}

}