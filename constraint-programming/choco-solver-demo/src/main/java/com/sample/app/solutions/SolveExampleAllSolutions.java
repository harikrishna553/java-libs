package com.sample.app.solutions;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

public class SolveExampleAllSolutions {
	public static void main(String[] args) {
		Model model = new Model("Solve Example");
		IntVar x = model.intVar("x", 0, 10); // Variable x in range [0, 10]
		IntVar y = model.intVar("y", 0, 10); // Variable y in range [0, 10]

		// Constraint: x + y = 10
		model.arithm(x, "+", y, "=", 10).post();

		Solver solver = model.getSolver();

		// Defines a limit over the run time in milliseconds
		solver.limitTime(10000);

		System.out.println("All Solutions....");
		while (solver.solve()) {
			System.out.print("x = " + x.getValue() + ", ");
			System.out.println("y = " + y.getValue());
		}

	}
}
