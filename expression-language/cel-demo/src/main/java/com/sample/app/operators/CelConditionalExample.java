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

public class CelConditionalExample {
	// Define CEL Compiler with variables
	private static final CelCompiler celCompiler = CelCompilerFactory.standardCelCompilerBuilder()
			.addVar("age", SimpleType.INT).addVar("isMember", SimpleType.BOOL).addVar("score", SimpleType.INT).build();

	// Define CEL Runtime
	private static final CelRuntime celRuntime = CelRuntimeFactory.standardCelRuntimeBuilder().build();

	public static void main(String[] args) {
		// Define input values
		Map<String, Object> variables = Map.of("age", 20, "isMember", true, "score", 75);

		// Evaluate different CEL conditional expressions
		evaluateExpression("age >= 18 ? 'Adult' : 'Minor'", variables); // Expected Output: "Adult"
		evaluateExpression("isMember ? 'Discount Applied' : 'No Discount'", variables); // Expected Output: "Discount
																						// Applied"
		evaluateExpression("score >= 50 ? (score >= 90 ? 'A' : 'B') : 'F'", variables); // Expected Output: "B"
	}

	/**
	 * Method to compile and evaluate a CEL expression
	 */
	private static void evaluateExpression(String expression, Map<String, Object> variables) {
		try {
			// Compile the CEL expression
			CelAbstractSyntaxTree ast = celCompiler.compile(expression).getAst();

			// Create a program from the compiled AST
			Program program = celRuntime.createProgram(ast);

			// Evaluate the expression with input variables
			Object result = program.eval(variables);

			// Print the result
			System.out.println("Expression: " + expression + " → Result: " + result);
		} catch (CelValidationException e) {
			System.err.println("CEL Validation Error: " + e.getMessage());
		} catch (CelEvaluationException e) {
			System.err.println("CEL Evaluation Error: " + e.getMessage());
		}
	}
}
