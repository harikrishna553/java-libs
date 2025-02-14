package com.sample.app.strings;

import dev.cel.common.CelAbstractSyntaxTree;
import dev.cel.common.CelValidationException;
import dev.cel.compiler.CelCompiler;
import dev.cel.compiler.CelCompilerFactory;
import dev.cel.runtime.CelEvaluationException;
import dev.cel.runtime.CelRuntime;
import dev.cel.runtime.CelRuntimeFactory;
import dev.cel.runtime.CelRuntime.Program;
import dev.cel.common.CelSource;
import dev.cel.common.types.SimpleType;

import java.util.Map;

public class CelStringFunctionsExample {
    // Define CEL Compiler with variables
    private static final CelCompiler celCompiler = CelCompilerFactory.standardCelCompilerBuilder()
        .addVar("str", SimpleType.STRING)
        .build();

    // Define CEL Runtime
    private static final CelRuntime celRuntime = CelRuntimeFactory.standardCelRuntimeBuilder().build();

    public static void main(String[] args) {
        // Define input string variables
        Map<String, Object> variables = Map.of(
            "str", "  Hello Google CEL World!  " // Example string
        );

        // Evaluate different CEL string functions
        evaluateExpression("size(str)", variables);        // Expected Output: 26
       
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
