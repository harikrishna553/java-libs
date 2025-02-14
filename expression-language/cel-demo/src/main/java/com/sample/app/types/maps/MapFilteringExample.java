package com.sample.app.types.maps;

import dev.cel.common.CelAbstractSyntaxTree;
import dev.cel.common.CelValidationException;
import dev.cel.compiler.CelCompiler;
import dev.cel.compiler.CelCompilerFactory;
import dev.cel.runtime.CelEvaluationException;
import dev.cel.runtime.CelRuntime;
import dev.cel.runtime.CelRuntimeFactory;

public class MapFilteringExample {
    public static void main(String[] args) throws CelValidationException, CelEvaluationException {
        String celExpression = "'age' in {'name': 'Alice', 'age': 30} && {'name': 'Alice', 'age': 30}['age'] > 25";

        CelCompiler celCompiler = CelCompilerFactory.standardCelCompilerBuilder().build();
        CelAbstractSyntaxTree ast = celCompiler.compile(celExpression).getAst();

        CelRuntime celRuntime = CelRuntimeFactory.standardCelRuntimeBuilder().build();
        CelRuntime.Program program = celRuntime.createProgram(ast);
        System.out.println("Is age greater than 25? " + program.eval());
    }
}

