package com.sample.app.variables;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.BoolVar;

public class BooleanVariableExample {
	public static void main(String[] args) {
		// Create a model
		Model model = new Model("Boolean Variable Example");

		// Example 1: Create a boolean variable that can be true or false
		BoolVar b1 = model.boolVar("b1");

		// Example 2: Create a boolean variable fixed to true
		BoolVar b2 = model.boolVar("b2", true);

		// Example 3: Create a boolean variable fixed to false
		BoolVar b3 = model.boolVar("b3", false);

		// Print the variables
		System.out.println(b1); // b1 can be true or false
		System.out.println(b2); // b2 is true
		System.out.println(b3); // b3 is false
	}
}
