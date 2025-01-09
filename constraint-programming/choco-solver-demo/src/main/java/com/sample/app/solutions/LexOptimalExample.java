package com.sample.app.solutions;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;

public class LexOptimalExample {
	public static void main(String[] args) {
		// Create a model
		Model model = new Model("Lexicographical Optimization Example");

		// Define variables
		IntVar a = model.intVar("a", 0, 5);
		IntVar b = model.intVar("b", 0, 20);
		IntVar c = model.intVar("c", 0, 10);
		IntVar target = model.intVar("target", 25); // Target value

		// Coefficients for the variables
		int[] coefficients = { 1, 1, 1 }; // Coefficients for a, b, c

		// Variables array
		IntVar[] variables = { a, b, c };

		// Scalar constraint: a + b + c = target
		model.scalar(variables, coefficients, "=", target).post();

		// Define objectives in order of priority (lexicographical optimization)
		IntVar[] objectives = { a, b, c }; // Optimize a first, then b

		// Find lexicographically optimal solution
		Solution lexOptimalSolution = model.getSolver().findLexOptimalSolution(objectives, Model.MAXIMIZE);

		// Print the lexicographically optimal solution
		if (lexOptimalSolution != null) {
			System.out.println("Lexicographically optimal solution:");
			System.out.println("a = " + lexOptimalSolution.getIntVal(a) + ", b = " + lexOptimalSolution.getIntVal(b)
					+ ", c = " + lexOptimalSolution.getIntVal(c));
		} else {
			System.out.println("No solution found.");
		}
	}
}
