package com.sample.app;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class ReferGeneratedFieldByName {
	public static void main(String args[]) throws IOException {

		MethodSpec.Builder sumOfNumbers = MethodSpec.methodBuilder("sum");
		sumOfNumbers.addParameter(int.class, "a", Modifier.FINAL);
		sumOfNumbers.addParameter(int.class, "b", Modifier.FINAL);

		sumOfNumbers.returns(int.class);
		sumOfNumbers.addStatement("return a + b");
		sumOfNumbers.addModifiers(Modifier.PRIVATE, Modifier.STATIC);

		MethodSpec sumOfNumbersSpec = sumOfNumbers.build();

		MethodSpec.Builder mainMethodSpecBuilder = MethodSpec.methodBuilder("main");
		mainMethodSpecBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
		mainMethodSpecBuilder.returns(void.class);
		mainMethodSpecBuilder.addParameter(String[].class, "args");

		mainMethodSpecBuilder.addCode("\n");
		mainMethodSpecBuilder.addStatement("int result = $N(10, 20)", sumOfNumbersSpec);
		mainMethodSpecBuilder.addStatement("System.out.println(\"Sum of 30 and 40 is : \"+ $N(30, 40))",
				sumOfNumbersSpec);
		mainMethodSpecBuilder.addCode("\n");

		MethodSpec mainMethodSpec = mainMethodSpecBuilder.build();

		String className = "Test";
		TypeSpec.Builder classSpecBuilder = TypeSpec.classBuilder(className);
		classSpecBuilder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
		classSpecBuilder.addMethod(mainMethodSpec);
		classSpecBuilder.addMethod(sumOfNumbersSpec);
		TypeSpec helloWorldTypeSpec = classSpecBuilder.build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, helloWorldTypeSpec).build();

		javaFile.writeTo(System.out);
	}

}
