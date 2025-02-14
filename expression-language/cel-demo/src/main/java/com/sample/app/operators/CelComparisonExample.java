package com.sample.app.operators;

import java.util.Map;

import dev.cel.common.CelAbstractSyntaxTree;
import dev.cel.common.CelValidationException;
import dev.cel.common.types.SimpleType;
import dev.cel.compiler.CelCompiler;
import dev.cel.compiler.CelCompilerFactory;
import dev.cel.runtime.CelEvaluationException;
import dev.cel.runtime.CelRuntime;
import dev.cel.runtime.CelRuntime.Program;
import dev.cel.runtime.CelRuntimeFactory;

public class CelComparisonExample {
	private static CelCompiler celCompiler = CelCompilerFactory.standardCelCompilerBuilder().addVar("x", SimpleType.INT)
			.addVar("y", SimpleType.INT).addVar("z", SimpleType.INT).addVar("w", SimpleType.INT)
			.addVar("v", SimpleType.INT).addVar("u", SimpleType.INT).build();

	public static void main(String[] args) {
		try {
			// Define CEL expressions using comparison operators
			String[] expressions = { "x == y", // Check if x is equal to y
					"x != y", // Check if x is not equal to y
					"x > z", // Check if x is greater than z
					"w < v", // Check if w is less than v
					"v >= u", // Check if v is greater than or equal to u
					"y <= z" // Check if y is less than or equal to z
			};

			// Define values for variables
			Map<String, Object> variables = Map.of("x", 5, "y", 10, "z", 2, "w", 15, "v", 20, "u", 20);

			// Create CEL Runtime
			CelRuntime celRuntime = CelRuntimeFactory.standardCelRuntimeBuilder().build();

			// Evaluate each expression
			for (String expr : expressions) {
				CelAbstractSyntaxTree ast = celCompiler.compile(expr).getAst();
				Program program = celRuntime.createProgram(ast);
				Object result = program.eval(variables);

				// Print the expression and result
				System.out.println(expr + " → " + result);
			}

		} catch (CelValidationException e) {
			System.err.println("CEL Validation Error: " + e.getMessage());
		} catch (CelEvaluationException e) {
			System.err.println("CEL Evaluation Error: " + e.getMessage());
		}
	}
}
