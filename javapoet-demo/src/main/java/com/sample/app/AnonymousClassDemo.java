package com.sample.app;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

public class AnonymousClassDemo {

	public static void main(String args[]) throws IOException {
		TypeSpec comparatorType = TypeSpec.anonymousClassBuilder("")
				.addSuperinterface(ParameterizedTypeName.get(Comparator.class, String.class))
				.addMethod(MethodSpec.methodBuilder("compare").addAnnotation(Override.class)
						.addModifiers(Modifier.PUBLIC).addParameter(String.class, "a").addParameter(String.class, "b")
						.returns(int.class).addStatement("return $N.length() - $N.length()", "a", "b").build())
				.build();

		MethodSpec sortByLengthMethodSpec = MethodSpec.methodBuilder("sortByLength").addModifiers(Modifier.STATIC)
				.addParameter(ParameterizedTypeName.get(List.class, String.class), "inputList")
				.addStatement("$T.sort($N, $L)", Collections.class, "inputList", comparatorType).build();

		MethodSpec mainMethodSpec = MethodSpec.methodBuilder("main").addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.returns(void.class).addParameter(String[].class, "args")
				.addStatement("sortByLength(asList(\"a\", \"abcd\", \"ab\"))").build();

		TypeSpec helloWorldTypeSpec = TypeSpec.classBuilder("Test").addModifiers(Modifier.PUBLIC, Modifier.FINAL)
				.addMethod(mainMethodSpec).addMethod(sortByLengthMethodSpec).build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, helloWorldTypeSpec).addStaticImport(Arrays.class, "asList")
				.build();

		javaFile.writeTo(System.out);
	}

}

