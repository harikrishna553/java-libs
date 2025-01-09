package com.sample.app.solutions;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

public class SolveExample {
	public static void main(String[] args) {
		Model model = new Model("Solve Example");
		IntVar x = model.intVar("x", 0, 10); // Variable x in range [0, 10]
		IntVar y = model.intVar("y", 0, 10); // Variable y in range [0, 10]

		// Constraint: x + y = 10
		model.arithm(x, "+", y, "=", 10).post();

		Solver solver = model.getSolver();
		
		// Defines a limit over the run time in milliseconds
		solver.limitTime(10000);

		if (solver.solve()) {
			System.out.println("Solution found:");
			System.out.println("x = " + x.getValue());
			System.out.println("y = " + y.getValue());
		} else if (solver.hasEndedUnexpectedly()) {
			System.out.println("Search Ended unexpectedly");
		} else {
			System.out.println("Problem has no solution.");
		}
	}
}
