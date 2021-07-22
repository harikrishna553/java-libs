package com.sample.app;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public class EnumDemo2 {

	public static void main(String args[]) throws IOException {

		TypeSpec helloWorldTypeSpec = TypeSpec.enumBuilder("MONTH").addModifiers(Modifier.PUBLIC)
				.addEnumConstant("JANUARY",
						TypeSpec.anonymousClassBuilder("$1L, \"$2L\"", 1, "31 Days")
								.addMethod(MethodSpec.methodBuilder("toString").addAnnotation(Override.class)
										.addModifiers(Modifier.PUBLIC).addStatement("return $S", "I am first month in the year.....!")
										.returns(String.class).build())
								.build())
				.addEnumConstant("FEBRUARY", TypeSpec.anonymousClassBuilder("$1L, \"$2L\"", 2, "28 or 29 Days").build())
				.addEnumConstant("MARCH", TypeSpec.anonymousClassBuilder("$1L, \"$2L\"", 3, "31 Days").build())
				.addEnumConstant("APRIL", TypeSpec.anonymousClassBuilder("$1L, \"$2L\"", 4, "30 Days").build())
				.addEnumConstant("MAY", TypeSpec.anonymousClassBuilder("$1L, \"$2L\"", 5, "31 Days").build())
				.addEnumConstant("JUNE", TypeSpec.anonymousClassBuilder("$1L, \"$2L\"", 6, "30 Days").build())
				.addEnumConstant("JULY", TypeSpec.anonymousClassBuilder("$1L, \"$2L\"", 7, "31 Days").build())
				.addEnumConstant("AUGUST", TypeSpec.anonymousClassBuilder("$1L, \"$2L\"", 8, "31 Days").build())
				.addEnumConstant("SEPTEMBER", TypeSpec.anonymousClassBuilder("$1L, \"$2L\"", 9, "30 Days").build())
				.addEnumConstant("OCTOBER", TypeSpec.anonymousClassBuilder("$1L, \"$2L\"", 10, "31 Days").build())
				.addEnumConstant("NOVEMBER", TypeSpec.anonymousClassBuilder("$1L, \"$2L\"", 11, "30 Days").build())
				.addEnumConstant("DECEMBER", TypeSpec.anonymousClassBuilder("$1L, \"$2L\"", 12, "31 Days").build())
				.addField(FieldSpec.builder(TypeName.INT, "seqNum").build())
				.addField(FieldSpec.builder(String.class, "message").build())
				.addMethod(MethodSpec.constructorBuilder().addParameter(TypeName.INT, "seqNum")
						.addParameter(String.class, "message").addStatement("this.$N = $N", "seqNum", "seqNum")
						.addStatement("this.$N = $N", "message", "message").build())
				.build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, helloWorldTypeSpec).build();

		javaFile.writeTo(System.out);
	}

}
