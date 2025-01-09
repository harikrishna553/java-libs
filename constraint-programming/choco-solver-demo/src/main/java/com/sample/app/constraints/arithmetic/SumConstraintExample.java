package com.sample.app.constraints.arithmetic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class SumConstraintExample {
	public static void main(String[] args) {
		Model model = new Model("Sum Constraint Example");

		IntVar v1 = model.intVar("v1", 1, 5);
		IntVar v2 = model.intVar("v2", 2, 6);
		IntVar v3 = model.intVar("v3", 3, 7);

		// Define variables
		IntVar[] vars = new IntVar[] { v1, v2, v3 };

		IntVar sumTarget = model.intVar("sumTarget", 10); // Target sum value

		// Post sum constraint: sum(vars) = sumTarget
		model.sum(vars, ">=", sumTarget).post();

		model.arithm(v1, "+", v2, "=", model.intScaleView(v3, 2)).post();

		// Solve and print solutions
		while (model.getSolver().solve()) {
			System.out.println("\nSolution:");
			for (IntVar var : vars) {
				System.out.println(var.getName() + " = " + var.getValue());
			}
			System.out.println("Sum Target = " + sumTarget.getValue());
		}
	}
}
