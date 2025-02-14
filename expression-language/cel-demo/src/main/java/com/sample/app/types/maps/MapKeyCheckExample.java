package com.sample.app.types.maps;

import dev.cel.common.CelAbstractSyntaxTree;
import dev.cel.common.CelValidationException;
import dev.cel.compiler.CelCompiler;
import dev.cel.compiler.CelCompilerFactory;
import dev.cel.runtime.CelEvaluationException;
import dev.cel.runtime.CelRuntime;
import dev.cel.runtime.CelRuntimeFactory;

public class MapKeyCheckExample {
	public static void main(String[] args) throws CelValidationException, CelEvaluationException {
		String celExpression = "'age' in {'name': 'Alice', 'age': 30}";

		CelCompiler celCompiler = CelCompilerFactory.standardCelCompilerBuilder().build();
		CelAbstractSyntaxTree ast = celCompiler.compile(celExpression).getAst();

		CelRuntime celRuntime = CelRuntimeFactory.standardCelRuntimeBuilder().build();
		CelRuntime.Program program = celRuntime.createProgram(ast);
		System.out.println("Is 'age' present in the map? " + program.eval());
	}
}
