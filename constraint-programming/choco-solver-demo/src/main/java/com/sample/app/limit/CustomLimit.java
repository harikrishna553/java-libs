package com.sample.app.limit;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.Variable;

public class CustomLimit {

	public static void main(String[] args) {
		// Create a new model
		Model model = new Model("Limit By Time Example");

		// Define variables with ranges
		IntVar x = model.intVar("x", 0, 10); // Variable x in range [0, 10]
		IntVar y = model.intVar("y", 0, 10); // Variable y in range [0, 10]

		model.arithm(x, "+", y, "=", 5).post();

		Solver solver = model.getSolver();
		solver.limitSearch(() -> {
			// Custom condition to stop the search
			return model.getSolver().getSolutionCount() > 5; // Stop after 5 solutions
		});

		while (solver.solve()) {
			// System.out.println("x = " + x.getValue() + ", y = " + y.getValue());
			Variable[] vars = model.getVars();
			for (Variable variable : vars) {
				System.out.println(variable.getName());

			}
		}

	}

}
