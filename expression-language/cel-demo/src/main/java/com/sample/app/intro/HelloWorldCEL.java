package com.sample.app.intro;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import dev.cel.common.CelAbstractSyntaxTree;
import dev.cel.common.CelValidationException;
import dev.cel.common.types.SimpleType;
import dev.cel.compiler.CelCompiler;
import dev.cel.compiler.CelCompilerFactory;
import dev.cel.runtime.CelEvaluationException;
import dev.cel.runtime.CelRuntime;
import dev.cel.runtime.CelRuntimeFactory;

public class HelloWorldCEL {
	private static final Logger LOGGER = Logger.getLogger(HelloWorldCEL.class.getName());

	// Construct the compilation and runtime environments.
	private static final CelCompiler CEL_COMPILER = CelCompilerFactory.standardCelCompilerBuilder()
			.addVar("my_var", SimpleType.STRING).build();
	private static final CelRuntime CEL_RUNTIME = CelRuntimeFactory.standardCelRuntimeBuilder().build();

	public static void main(String[] args) {
		HelloWorldCEL celDemo = new HelloWorldCEL();
		celDemo.run();
	}

	public void run() {
		String expression = "my_var + '!'";
		Map<String, Object> variables = Map.of("my_var", "Hello World");

		try {
			String result = evaluateExpression(expression, variables);
			LOGGER.info("CEL Evaluation Result: " + result);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error evaluating CEL expression", e);
		}
	}

	private String evaluateExpression(String expression, Map<String, Object> variables)
			throws CelValidationException, CelEvaluationException {

		LOGGER.info("Compiling CEL expression: " + expression);

		// Compile the expression into an Abstract Syntax Tree.
		CelAbstractSyntaxTree ast = CEL_COMPILER.compile(expression).getAst();

		// Plan an executable program instance.
		CelRuntime.Program program = CEL_RUNTIME.createProgram(ast);

		// Evaluate the program with input variables.
		return (String) program.eval(variables);
	}
}
