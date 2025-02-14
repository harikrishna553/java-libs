package com.sample.app.intro;

import java.util.Map;

import dev.cel.common.CelAbstractSyntaxTree;
import dev.cel.common.types.SimpleType;
import dev.cel.compiler.CelCompiler;
import dev.cel.compiler.CelCompilerFactory;
import dev.cel.runtime.CelRuntime;
import dev.cel.runtime.CelRuntimeFactory;

public class PrimitiveTypes {

	private static CelCompiler celCompiler = CelCompilerFactory.standardCelCompilerBuilder()
			.addVar("age", SimpleType.INT).addVar("isMember", SimpleType.BOOL).addVar("hasPaid", SimpleType.BOOL)
			.addVar("price", SimpleType.INT).addVar("radius", SimpleType.DOUBLE).addVar("quantity", SimpleType.INT)
			.addVar("firstName", SimpleType.STRING).addVar("lastName", SimpleType.STRING)
			.addVar("userName", SimpleType.STRING).addVar("name", SimpleType.STRING).addVar("marks", SimpleType.INT)
			.build();

	private static CelRuntime celRuntime = CelRuntimeFactory.standardCelRuntimeBuilder().build();

	private static void evaluateExpression(String expression, Map<String, Object> variables) throws Exception {
		// Compile the expression into an Abstract Syntax Tree
		CelAbstractSyntaxTree ast = celCompiler.compile(expression).getAst();
		// Plan an executable program instance
		CelRuntime.Program program = celRuntime.createProgram(ast);
		System.out.println(expression + " is evaluated to : '" + program.eval(variables) + "'");
	}

	public static void main(String[] args) throws Exception {

		String expression = "age >= 18";
		Map<String, Object> variables = Map.of("age", 20);
		evaluateExpression(expression, variables);

		expression = "isMember && hasPaid";
		variables = Map.of("isMember", true, "hasPaid", false);
		evaluateExpression(expression, variables);

		expression = "price * quantity";
		variables = Map.of("price", 50, "quantity", 3);
		evaluateExpression(expression, variables);

		expression = "marks >= 50";
		variables = Map.of("marks", 45);
		evaluateExpression(expression, variables);

		expression = "5 / 2";
		evaluateExpression(expression, Map.of());

		expression = "radius * 3.14 * 2.0";
		variables = Map.of("radius", 5.5);
		evaluateExpression(expression, variables);

		expression = "5.0 / 2.0";
		evaluateExpression(expression, Map.of());

		expression = "firstName + ' ' + lastName";
		variables = Map.of("firstName", "John", "lastName", "Doe");
		evaluateExpression(expression, variables);

		expression = "name == 'Alice'";
		variables = Map.of("name", "Alice");
		evaluateExpression(expression, variables);
	}

}
