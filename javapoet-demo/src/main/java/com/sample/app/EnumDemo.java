package com.sample.app;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

public class EnumDemo {

	public static void main(String args[]) throws IOException {

		TypeSpec helloWorldTypeSpec = TypeSpec.enumBuilder("MONTH")
			    .addModifiers(Modifier.PUBLIC)
			    .addEnumConstant("JANUARY")
			    .addEnumConstant("FEBRUARY")
			    .addEnumConstant("MARCH")
			    .addEnumConstant("APRIL")
			    .addEnumConstant("MAY")
			    .addEnumConstant("JUNE")
			    .addEnumConstant("JULY")
			    .addEnumConstant("AUGUST")
			    .addEnumConstant("SEPTEMBER")
			    .addEnumConstant("OCTOBER")
			    .addEnumConstant("NOVEMBER")
			    .addEnumConstant("DECEMBER")
			    .build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, helloWorldTypeSpec).build();

		javaFile.writeTo(System.out);
	}

}
