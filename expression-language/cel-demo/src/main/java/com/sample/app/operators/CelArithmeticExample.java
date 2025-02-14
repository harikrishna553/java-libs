package com.sample.app.operators;

import java.util.Map;

import dev.cel.common.CelAbstractSyntaxTree;
import dev.cel.common.CelSource;
import dev.cel.common.CelValidationException;
import dev.cel.common.types.SimpleType;
import dev.cel.compiler.CelCompiler;
import dev.cel.compiler.CelCompilerFactory;
import dev.cel.runtime.CelEvaluationException;
import dev.cel.runtime.CelRuntime;
import dev.cel.runtime.CelRuntime.Program;
import dev.cel.runtime.CelRuntimeFactory;

public class CelArithmeticExample {
	private static CelCompiler celCompiler = CelCompilerFactory.standardCelCompilerBuilder().addVar("x", SimpleType.INT)
			.addVar("y", SimpleType.INT).addVar("z", SimpleType.INT).addVar("w", SimpleType.INT)
			.addVar("v", SimpleType.INT).addVar("u", SimpleType.INT).build();

	public static void main(String[] args) {
		try {
			// Define a CEL expression using declared variables
			String celExpression = "x + y - z * w / v % u";

			// Compile the expression
			CelAbstractSyntaxTree ast = celCompiler.compile(celExpression).getAst();

			// Create a CEL Runtime
			CelRuntime celRuntime = CelRuntimeFactory.standardCelRuntimeBuilder().build();

			// Create a program from AST
			Program program = celRuntime.createProgram(ast);

			// Define values for the declared CEL variables
			Map<String, Object> variables = Map.of("x", 10, "y", 5, "z", 3, "w", 4, "v", 2, "u", 3);

			// Evaluate the expression
			Object result = program.eval(variables);

			// Print the result
			System.out.println("Result: " + result);

		} catch (CelValidationException e) {
			System.err.println("CEL Validation Error: " + e.getMessage());
		} catch (CelEvaluationException e) {
			System.err.println("CEL Evaluation Error: " + e.getMessage());
		}
	}
}
