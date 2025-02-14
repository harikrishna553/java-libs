package com.sample.app.policy;

import java.util.List;
import java.util.Map;

import dev.cel.common.CelAbstractSyntaxTree;
import dev.cel.common.types.ListType;
import dev.cel.common.types.SimpleType;
import dev.cel.compiler.CelCompiler;
import dev.cel.compiler.CelCompilerFactory;
import dev.cel.runtime.CelRuntime;
import dev.cel.runtime.CelRuntimeFactory;

public class PolicyEvaluator {

	public static boolean evaluatePolicy(RBAPolicy policy, String expression) {
		try {
			CelCompiler celCompiler = CelCompilerFactory.standardCelCompilerBuilder().addVar("title", SimpleType.STRING)
					.addVar("adGroups", ListType.create(SimpleType.STRING))
					.addVar("location", ListType.create(SimpleType.STRING))
					.addVar("country", ListType.create(SimpleType.STRING)).addVar("manager", SimpleType.STRING)
					.addVar("userId", SimpleType.STRING).build();

			CelAbstractSyntaxTree ast = celCompiler.compile(expression).getAst();

			CelRuntime celRuntime = CelRuntimeFactory.standardCelRuntimeBuilder().build();
			CelRuntime.Program program = celRuntime.createProgram(ast);

			Map<String, Object> variables = Map.of("title", policy.getTitle(), "adGroups", policy.getAdGroups(),
					"location", policy.getLocation(), "country", policy.getCountry(), "manager", policy.getManager(),
					"userId", policy.getUserId());

			return (boolean) program.eval(variables);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public static void main(String[] args) {
		RBAPolicy policy = new RBAPolicy("Staff Software Engineer", List.of("group1", "group2"), List.of("Bangalore"),
				List.of("IN"), "Manager Id", "krishna");
		String expression = "title == 'Staff Software Engineer' && 'group1' in adGroups";
		boolean result = evaluatePolicy(policy, expression);
		System.out.println("Policy Evaluation Result: " + result);
	}
}