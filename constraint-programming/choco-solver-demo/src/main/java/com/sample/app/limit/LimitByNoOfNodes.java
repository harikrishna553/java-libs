package com.sample.app.limit;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

public class LimitByNoOfNodes {
	public static void main(String[] args) {
		// Create a new model
		Model model = new Model("Limit By Time Example");

		// Define variables with ranges
		IntVar x = model.intVar("x", 0, 10000); // Variable x in range [0, 10]
		IntVar y = model.intVar("y", 0, 10000); // Variable y in range [0, 10]

		model.arithm(x, "*", y, "=", 1234567).post();

		Solver solver = model.getSolver();
		solver.limitBacktrack(2); 

		if (solver.solve()) {
			System.out.println("x = " + x.getValue() + ", y = " + y.getValue());
		} else {
			System.out.println("No solution found");
		}

	}
}
