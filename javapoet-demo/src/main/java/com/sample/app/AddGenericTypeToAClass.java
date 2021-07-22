package com.sample.app;

import java.io.IOException;
import java.util.Arrays;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

public class AddGenericTypeToAClass {
	public static void main(String args[]) throws IOException {

		TypeVariableName typeVariableName1 = TypeVariableName.get("T");
		TypeVariableName typeVariableName2 = TypeVariableName.get("U");
		FieldSpec idField = FieldSpec.builder(typeVariableName1, "id").build();
		FieldSpec keyField = FieldSpec.builder(typeVariableName2, "key").build();

		TypeSpec helloWorldTypeSpec = TypeSpec.classBuilder("Test")
				.addTypeVariables(Arrays.asList(typeVariableName1, typeVariableName2)).addField(keyField)
				.addField(idField).build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, helloWorldTypeSpec).build();

		javaFile.writeTo(System.out);
	}

}

