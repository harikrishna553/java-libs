package com.sample.app.limit;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

public class LimitByNoOfSolutions {

	public static void main(String[] args) {
		// Create a new model
		Model model = new Model("Limit By Time Example");

		// Define variables with ranges
		IntVar x = model.intVar("x", 0, 10); // Variable x in range [0, 10]
		IntVar y = model.intVar("y", 0, 10); // Variable y in range [0, 10]

		model.arithm(x, "+", y, "=", 5).post();

		Solver solver = model.getSolver();
		solver.limitSolution(5);

		while (solver.solve()) {
			System.out.println("x = " + x.getValue() + ", y = " + y.getValue());
		}

	}

}
