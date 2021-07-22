package com.sample.app;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class AddParametersDemo2 {

	public static void main(String args[]) throws IOException {
		FieldSpec nameField = FieldSpec.builder(String.class, "name")
			    .addModifiers(Modifier.PRIVATE)
			    .build();
		
		FieldSpec idField = FieldSpec.builder(int.class, "id")
			    .addModifiers(Modifier.PRIVATE)
			    .build();

		MethodSpec constructor = MethodSpec.constructorBuilder()
					.addModifiers(Modifier.PUBLIC)
					.addParameter(int.class, "id", Modifier.FINAL)
					.addParameter(String.class, "name", Modifier.FINAL)
					.addStatement("this.$N = $N", "id", "id")
					.addStatement("this.$N = $N", "name", "name").build();
		
		String className = "Employee";
		TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className);
		classBuilder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
		classBuilder.addMethod(constructor);
		classBuilder.addField(nameField).addField(idField);
		TypeSpec helloWorldTypeSpec = classBuilder.build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, helloWorldTypeSpec).build();

		javaFile.writeTo(System.out);
	}

}