package com.sample.app;

import java.io.IOException;

import javax.annotation.Resource;
import javax.lang.model.element.Modifier;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class JavaDocDemo {

	public static void main(String args[]) throws IOException {

		MethodSpec toStringSpec = MethodSpec.methodBuilder("connectionString").addModifiers(Modifier.PUBLIC)
				.addStatement("return jdbc:h2:file:~/db/myOrg.db;DB_CLOSE_ON_EXIT=FALSE;DB_CLOSE_DELAY=-1;")
				.addJavadoc("This represent a {@link $T} to connect to the database", Resource.class)
				.returns(String.class).addAnnotation(AnnotationSpec.builder(Resource.class)
						.addMember("name", "$S", "datasource").addMember("shareable", "false").build())
				.build();

		String className = "HelloWorld";
		TypeSpec.Builder classBuilder = TypeSpec.classBuilder(className);
		classBuilder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
		classBuilder.addMethod(toStringSpec);
		TypeSpec helloWorldTypeSpec = classBuilder.build();

		String packageName = "com.sample.app";
		JavaFile javaFile = JavaFile.builder(packageName, helloWorldTypeSpec).build();

		javaFile.writeTo(System.out);
	}

}
