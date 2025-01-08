package com.sample.app.variables;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;

public class DynamicBoundedDomainExample {
	public static void main(String[] args) throws ContradictionException {
		// Step 1: Create a Model
		Model model = new Model("Dynamic Upper Bound Updates");

		// Step 2: Create a variable with a bounded domain [1, 10]
		IntVar v = model.intVar("v", 1, 10, true);

		model.arithm(v, ">", 8).post();

		// Step 3: Dynamically update the upper bound of the variable
		v.updateUpperBound(8, null); // Update the upper bound to 8

		// Solve and print the results
		if (model.getSolver().solve()) {
			System.out.println("Solution found:");
			System.out.println("v = " + v.getValue()); // The value will be in the range [1, 8]
		} else {
			System.out.println("No solution found.");
		}
	}
}
