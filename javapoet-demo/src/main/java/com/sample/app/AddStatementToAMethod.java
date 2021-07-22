package com.sample.app;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class AddStatementToAMethod {

	public static void main(String args[]) throws IOException {

		MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder("main");
		methodSpecBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
		methodSpecBuilder.returns(void.class);
		methodSpecBuilder.addParameter(String[].class, "args");

		methodSpecBuilder.addCode("\n");
		methodSpecBuilder.addComment("Print stars triangle");
		methodSpecBuilder.addStatement("int i = 0, j = 0");
		methodSpecBuilder.beginControlFlow("while(i < 10)");
		methodSpecBuilder.beginControlFlow("while(j < 10)");
		methodSpecBuilder.addStatement("System.out.print(\"*\")");
		methodSpecBuilder.addStatement("j++");
		methodSpecBuilder.endControlFlow();
		methodSpecBuilder.addStatement("i++");
		methodSpecBuilder.addStatement("j = 0");
		methodSpecBuilder.addCode("\n");
		methodSpecBuilder.endControlFlow();

		MethodSpec mainMethodSpec = methodSpecBuilder.build();

		String className = "HelloWorld";
		TypeSpec.Builder classSpecBuilder = TypeSpec.classBuilder(className);
		classSpecBuilder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
		classSpecBuilder.addMethod(mainMethodSpec);
		TypeSpec helloWorldTypeSpec = classSpecBuilder.build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, helloWorldTypeSpec).build();

		javaFile.writeTo(System.out);
	}

}
