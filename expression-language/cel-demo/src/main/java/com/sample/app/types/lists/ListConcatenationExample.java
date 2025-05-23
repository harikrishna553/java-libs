package com.sample.app.types.lists;

import dev.cel.common.CelAbstractSyntaxTree;
import dev.cel.common.CelValidationException;
import dev.cel.compiler.CelCompiler;
import dev.cel.compiler.CelCompilerFactory;
import dev.cel.runtime.CelEvaluationException;
import dev.cel.runtime.CelRuntime;
import dev.cel.runtime.CelRuntimeFactory;

public class ListConcatenationExample {
	public static void main(String[] args) throws CelValidationException, CelEvaluationException {
		String celExpression = "[1, 2, 3] + [4, 5, 6]";

		CelCompiler celCompiler = CelCompilerFactory.standardCelCompilerBuilder().build();
		CelAbstractSyntaxTree ast = celCompiler.compile(celExpression).getAst();

		CelRuntime celRuntime = CelRuntimeFactory.standardCelRuntimeBuilder().build();
		CelRuntime.Program program = celRuntime.createProgram(ast);
		System.out.println("Concatenated list: " + program.eval());
	}
}
