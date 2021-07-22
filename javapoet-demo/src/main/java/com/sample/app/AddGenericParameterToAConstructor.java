package com.sample.app;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

public class AddGenericParameterToAConstructor {
	public static void main(String args[]) throws IOException {

		ParameterSpec empName = ParameterSpec.builder(String.class, "name").addModifiers(Modifier.FINAL).build();

		TypeVariableName typeVariableName = TypeVariableName.get("T");
		FieldSpec idField = FieldSpec.builder(typeVariableName, "id").build();

		MethodSpec constructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).addParameter(empName)
				.addParameter(typeVariableName, "id").addStatement("this.$N = $N", "id", "id")
				.addStatement("this.$N = $N", "name", "name").build();

		FieldSpec nameField = FieldSpec.builder(String.class, "name").build();
		TypeSpec helloWorldTypeSpec = TypeSpec.classBuilder("Employee").addTypeVariable(typeVariableName)
				.addModifiers(Modifier.PUBLIC, Modifier.FINAL).addMethod(constructor).addField(nameField)
				.addField(idField).build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, helloWorldTypeSpec).build();

		javaFile.writeTo(System.out);
	}

}
