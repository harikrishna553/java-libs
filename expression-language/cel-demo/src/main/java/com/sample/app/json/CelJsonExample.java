package com.sample.app.json;

import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import dev.cel.compiler.CelCompiler;
import dev.cel.compiler.CelCompilerFactory;
import dev.cel.common.CelAbstractSyntaxTree;
import dev.cel.common.types.SimpleType;
import dev.cel.runtime.CelRuntime;
import dev.cel.runtime.CelRuntimeFactory;
import dev.cel.runtime.CelRuntime.Program;

public class CelJsonExample {
	public static void main(String[] args) throws Exception {
		// Sample JSON input
		String json = """
				{
				    "user": {
				        "name": "Krishna",
				        "age": 25,
				        "status": "Active"
				    },
				    "x": 10
				}
				""";

		// Parse JSON into a Java Map with numbers as Long
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(DeserializationFeature.USE_LONG_FOR_INTS);
		Map<String, Object> jsonData = objectMapper.readValue(json, Map.class);

		// CEL Expression: Multiply user's age by 2
		String celExpression = "user.age * 2";

		// Compile the CEL expression with variable definitions
		CelCompiler celCompiler = CelCompilerFactory.standardCelCompilerBuilder().addVar("user", SimpleType.DYN)
				.addVar("x", SimpleType.INT).build();
		CelAbstractSyntaxTree ast = celCompiler.compile(celExpression).getAst();

		// Create a CEL runtime and compile the program
		CelRuntime celRuntime = CelRuntimeFactory.standardCelRuntimeBuilder().build();
		Program program = celRuntime.createProgram(ast);

		// Evaluate the expression against JSON data
		Object result = program.eval(jsonData);
		System.out.println("Result: " + result); // Expected output: 50
	}
}
