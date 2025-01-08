package com.sample.app.variables;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class BoundedDomainExample {
	public static void main(String[] args) {
		// Create a Model
		Model model = new Model("Bounded Domain Example");

		// Create a variable with a bounded domain [1, 12]
		IntVar v = model.intVar("v", 1, 12, true);

		// Add a constraint: v must be less than or equal to 10 and greater than 7
		model.arithm(v, "<=", 10).post();
		model.arithm(v, ">", 7).post();

		// Solve and print the results
		if (model.getSolver().solve()) {
			System.out.println("Solution found:");
			System.out.println("v = " + v.getValue());
		} else {
			System.out.println("No solution found.");
		}
	}
}
