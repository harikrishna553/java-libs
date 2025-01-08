package com.sample.app.variables;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class EnumeratedDomainExample {
	public static void main(String[] args) {
		// Create a model for the problem
		Model model = new Model("Enumerated Domain Example");

		// Example 1: Variable with an enumerated domain using bounds
		IntVar v1 = model.intVar("v1", 1, 4, false);
		// v1 can take values {1, 2, 3, 4}

		// Example 2: Variable with an enumerated domain using an array
		IntVar v2 = model.intVar("v2", new int[] { 1, 3, 5 });
		// v2 can take values {1, 3, 5}

		// Example 3: Variable without specifying boundedDomain
		IntVar v3 = model.intVar("v3", 1, 100);
		// The engine will decide if v3 uses a bounded or enumerated domain based on the
		// range

		// Printing the variables
		System.out.println(v1);
		System.out.println(v2);
		System.out.println(v3);
	}
}
