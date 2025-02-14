package com.sample.app.types;

import dev.cel.common.CelAbstractSyntaxTree;
import dev.cel.common.CelValidationException;
import dev.cel.compiler.CelCompiler;
import dev.cel.compiler.CelCompilerFactory;
import dev.cel.runtime.CelEvaluationException;
import dev.cel.runtime.CelRuntime;
import dev.cel.runtime.CelRuntimeFactory;
import dev.cel.runtime.CelRuntime.Program;

public class VariableTypeEValuator {

	public static void main(String[] args) throws CelValidationException, CelEvaluationException {
		System.out.println(evaluateExpression("type(1)"));
		System.out.println(evaluateExpression("type(\"a\")"));
		System.out.println(evaluateExpression("type(1) == int"));
		System.out.println(evaluateExpression("type(1) == string"));
		System.out.println(evaluateExpression("type(type(1)) == type(string)"));
	}

	private static Object evaluateExpression(String expression) throws CelValidationException, CelEvaluationException {
		// Construct the compilation and runtime environments.
		CelCompiler celCompiler = CelCompilerFactory.standardCelCompilerBuilder().build();
		CelRuntime celRuntime = CelRuntimeFactory.standardCelRuntimeBuilder().build();

		CelAbstractSyntaxTree ast = celCompiler.compile(expression).getAst();

		Program program = celRuntime.createProgram(ast);
		return program.eval();
	}
}
