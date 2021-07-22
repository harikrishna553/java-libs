package com.sample.app;
import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class MethodDefinitionWithSomeConfig {
	
	private static MethodSpec dynamicMethod(String name, int startingNumber, int endingNumber, char operator) {		 
		return MethodSpec.methodBuilder(name)
			      .returns(int.class)
			      .addStatement("int result = 0")
			      .beginControlFlow("for (int i = " + startingNumber + "; i <= " + endingNumber + "; i++)")
			      .addStatement("result = result " + operator + " i")
			      .endControlFlow().addStatement("return result")
				.build();
	}
	
	public static void main(String args[]) throws IOException {
		MethodSpec addFrom10To20 = dynamicMethod("addFrom10To20", 10, 20, '+');
		MethodSpec addFrist30Numbers = dynamicMethod("addFrom0To30", 10, 30, '+');
		
		String className = "HelloWorld";
		TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className);
		classBuilder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
		classBuilder.addMethod(addFrom10To20);
		classBuilder.addMethod(addFrist30Numbers);
		TypeSpec helloWorldTypeSpec = classBuilder.build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, helloWorldTypeSpec).build();

		javaFile.writeTo(System.out);
	}

}


