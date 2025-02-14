package com.sample.app.types.lists;

import java.util.List;
import java.util.Map;

import dev.cel.common.CelAbstractSyntaxTree;
import dev.cel.common.CelValidationException;
import dev.cel.common.types.ListType;
import dev.cel.common.types.SimpleType;
import dev.cel.compiler.CelCompiler;
import dev.cel.compiler.CelCompilerFactory;
import dev.cel.runtime.CelEvaluationException;
import dev.cel.runtime.CelRuntime;
import dev.cel.runtime.CelRuntimeFactory;

public class ListMembershipExampleUsingVariables {
	public static void main(String[] args) throws CelValidationException, CelEvaluationException {
		// Define CEL expression using variables
		String celExpression = "ele in myList";

		// Configure CEL compiler with variable declarations
		CelCompiler celCompiler = CelCompilerFactory.standardCelCompilerBuilder().addVar("ele", SimpleType.INT)
				.addVar("myList", ListType.create(SimpleType.INT)).build();

		CelAbstractSyntaxTree ast = celCompiler.compile(celExpression).getAst();

		// Create CEL runtime
		CelRuntime celRuntime = CelRuntimeFactory.standardCelRuntimeBuilder().build();
		CelRuntime.Program program = celRuntime.createProgram(ast);

		// Define variables
		Map<String, Object> variables = Map.of("ele", 10, "myList", List.of(5, 10, 15));

		// Evaluate the expression
		Object result = program.eval(variables);
		System.out.println("Is " + variables.get("ele") + " in the list? " + result);
	}
}
