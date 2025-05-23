package com.sample.app.types.maps;

import dev.cel.common.CelAbstractSyntaxTree;
import dev.cel.common.CelValidationException;
import dev.cel.compiler.CelCompiler;
import dev.cel.compiler.CelCompilerFactory;
import dev.cel.runtime.CelEvaluationException;
import dev.cel.runtime.CelRuntime;
import dev.cel.runtime.CelRuntimeFactory;

public class NestedMapExample {
	public static void main(String[] args) throws CelValidationException, CelEvaluationException {
		String celExpression = "{'person': {'name': 'Alice', 'age': 30}}['person']['name']";

		CelCompiler celCompiler = CelCompilerFactory.standardCelCompilerBuilder().build();
		CelAbstractSyntaxTree ast = celCompiler.compile(celExpression).getAst();

		CelRuntime celRuntime = CelRuntimeFactory.standardCelRuntimeBuilder().build();
		CelRuntime.Program program = celRuntime.createProgram(ast);
		System.out.println("Person's name: " + program.eval());
	}
}
