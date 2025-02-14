package com.sample.app.types.maps;

import java.util.Map;

import dev.cel.common.CelAbstractSyntaxTree;
import dev.cel.common.CelValidationException;
import dev.cel.common.types.MapType;
import dev.cel.common.types.SimpleType;
import dev.cel.compiler.CelCompiler;
import dev.cel.compiler.CelCompilerFactory;
import dev.cel.runtime.CelEvaluationException;
import dev.cel.runtime.CelRuntime;
import dev.cel.runtime.CelRuntime.Program;
import dev.cel.runtime.CelRuntimeFactory;

public class MapTypeWithVariablesDefiniton {

	// Define CEL Compiler with 'dataMap'
	private static final CelCompiler celCompiler = CelCompilerFactory.standardCelCompilerBuilder()
			.addVar("dataMap", MapType.create(SimpleType.STRING, SimpleType.STRING)).build();

	// Define CEL Runtime
	private static final CelRuntime celRuntime = CelRuntimeFactory.standardCelRuntimeBuilder().build();

	public static void main(String[] args) {
		// Sample data for dataMap
		Map<String, String> dataMap = Map.of("1", "Apple", "2", "Banana", "3", "Cherry");

		// Run different CEL expressions
		evaluateExpression("dataMap['1']", dataMap); // Expected Output: Apple
		evaluateExpression("'1' in dataMap", dataMap); // Expected Output: true
		evaluateExpression("size(dataMap)", dataMap); // Expected Output: 3
	}

	/**
	 * Method to compile and evaluate a CEL expression
	 */
	private static void evaluateExpression(String expression, Map<String, String> dataMap) {
		try {
			// Compile the CEL expression
			CelAbstractSyntaxTree ast = celCompiler.compile(expression).getAst();

			// Create a program from the compiled AST
			Program program = celRuntime.createProgram(ast);

			// Evaluate the expression with dataMap
			Object result = program.eval(Map.of("dataMap", dataMap));

			// Print the result
			System.out.println("Expression: " + expression + " → Result: " + result);
		} catch (CelValidationException e) {
			System.err.println("CEL Validation Error: " + e.getMessage());
		} catch (CelEvaluationException e) {
			e.printStackTrace();
			System.err.println("CEL Evaluation Error: " + e.getMessage());
		}
	}
}
