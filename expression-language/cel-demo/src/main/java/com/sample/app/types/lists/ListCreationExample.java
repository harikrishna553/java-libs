package com.sample.app.types.lists;

import dev.cel.common.CelAbstractSyntaxTree;
import dev.cel.common.CelValidationException;
import dev.cel.compiler.CelCompiler;
import dev.cel.compiler.CelCompilerFactory;
import dev.cel.runtime.CelEvaluationException;
import dev.cel.runtime.CelRuntime;
import dev.cel.runtime.CelRuntimeFactory;

public class ListCreationExample {
    public static void main(String[] args) throws CelValidationException, CelEvaluationException {
        String celExpression = "[10, 20, 30, 40]";

        CelCompiler celCompiler = CelCompilerFactory.standardCelCompilerBuilder().build();
        CelAbstractSyntaxTree ast = celCompiler.compile(celExpression).getAst();

        CelRuntime celRuntime = CelRuntimeFactory.standardCelRuntimeBuilder().build();
        CelRuntime.Program program = celRuntime.createProgram(ast);
        System.out.println("List created: " + program.eval());
    }
}
