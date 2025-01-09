package com.sample.app.solutions;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;

public class FindSolutionExample {
	public static void main(String[] args) {
		Model model = new Model("Find Solution Example");
		IntVar x = model.intVar("x", 0, 10); // Variable x in range [0, 10]
		IntVar y = model.intVar("y", 0, 10); // Variable y in range [0, 10]

		// Constraint: x + y = 10
		model.arithm(x, "+", y, "=", 10).post();

		Solution solution = model.getSolver().findSolution();
		if (solution != null) {
			System.out.println("Solution found:");
			System.out.println("x = " + solution.getIntVal(x));
			System.out.println("y = " + solution.getIntVal(y));
		} else {
			System.out.println("No solution found.");
		}
	}
}
