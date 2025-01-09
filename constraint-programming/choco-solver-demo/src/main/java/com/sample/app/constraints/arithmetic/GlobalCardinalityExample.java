package com.sample.app.constraints.arithmetic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class GlobalCardinalityExample {
	public static void main(String[] args) {
		Model model = new Model("Global Cardinality Constraint Example");

		// Array of variables
		IntVar[] vars = model.intVarArray("X", 8, 0, 6);

		// Values to count occurrences
		int[] values = new int[] { 2, 3, 5 };

		// Array of occurrence variables
		IntVar[] occs = model.intVarArray("O", 3, 0, 3);

		// Apply the global cardinality constraint
		model.globalCardinality(vars, values, occs, true).post();

		// Additional Constraints
		model.arithm(vars[0], "+", vars[1], "=", 10).post();
		model.arithm(vars[1], "+", vars[4], "=", 10).post();

		// Solve the model
		if (model.getSolver().solve()) {
			System.out.println("Solution:");
			System.out.println("vars:");
			for (int i = 0; i < vars.length; i++) {
				System.out.println("vars[" + i + "] = " + vars[i].getValue());
			}
			System.out.println("Occurrences:");
			for (int i = 0; i < occs.length; i++) {
				System.out.println("occs[" + i + "] (value " + values[i] + ") = " + occs[i].getValue());
			}
		} else {
			System.out.println("No solution found!");
		}
	}
}
