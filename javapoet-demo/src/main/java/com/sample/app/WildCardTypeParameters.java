package com.sample.app;

import java.io.IOException;
import java.util.List;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

public class WildCardTypeParameters {
	public static void main(String args[]) throws IOException {
		ParameterizedTypeName parameterizedTypeName1 = ParameterizedTypeName.get(ClassName.get(Class.class),
				WildcardTypeName.subtypeOf(Number.class));
		ParameterSpec parameterSpec1 = ParameterSpec.builder(parameterizedTypeName1, "param1", Modifier.FINAL).build();

		ParameterizedTypeName parameterizedTypeName2 = ParameterizedTypeName.get(ClassName.get(Class.class),
				WildcardTypeName.subtypeOf(List.class));
		ParameterSpec parameterSpec2 = ParameterSpec.builder(parameterizedTypeName2, "param2", Modifier.FINAL).build();

		MethodSpec demoMethod = MethodSpec.methodBuilder("demo").addModifiers(Modifier.PUBLIC)
				.addParameter(parameterSpec1).addParameter(parameterSpec2).build();

		TypeSpec helloWorldTypeSpec = TypeSpec.classBuilder("Test").addMethod(demoMethod).build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, helloWorldTypeSpec).build();

		javaFile.writeTo(System.out);
	}

}