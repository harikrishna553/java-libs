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

public class CelLogicalOperatorsExample {
	private static CelCompiler celCompiler = CelCompilerFactory.standardCelCompilerBuilder().addVar("x", SimpleType.INT)
			.addVar("y", SimpleType.INT).addVar("z", SimpleType.INT).addVar("isMember", SimpleType.BOOL)
			.addVar("hasPaid", SimpleType.BOOL).build();

	public static void main(String[] args) {
		try {
			// Define CEL expressions using logical operators
			String[] expressions = { "x > 5 && y < 10", // AND
					"x > 5 || y < 10", // OR
					"!isMember", // NOT
					"isMember && hasPaid", // Both conditions must be true
					"isMember || hasPaid" // At least one condition must be true
			};

			// Define values for variables
			Map<String, Object> variables = Map.of("x", 6, "y", 8, "z", 2, "isMember", true, "hasPaid", false);

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
