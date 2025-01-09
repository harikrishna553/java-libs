package com.sample.app.constraints.arithmetic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class CountConstraintExample {
	public static void main(String[] args) {
		Model model = new Model("Count Constraint Example");

		// Define the result variable for occurrences of 0
		IntVar occ0 = model.intVar("occ0", 0, 5);

		// Define an array of 7 variables with values ranging from 0 to 5
		IntVar[] vars = model.intVarArray("X", 7, 0, 5);

		// Apply the count constraint: count occurrences of 0 in vars
		model.count(0, vars, occ0).post();

		// Some constraints
		model.arithm(vars[0], "+", vars[1], "=", vars[2]).post();
		model.arithm(vars[0], "+", vars[2], "=", vars[3]).post();

		// Solve the model
		if (model.getSolver().solve()) {
			System.out.println("Solution:");
			System.out.println("Occurrences of 0: " + occ0.getValue());
			for (int i = 0; i < vars.length; i++) {
				System.out.println("vars[" + i + "] = " + vars[i].getValue());
			}
		} else {
			System.out.println("No solution found!");
		}
	}
}
