package com.sample.app.constraints;

import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;

public class AllDifferentExample {
	public static void main(String[] args) {
		// Create a model
		Model model = new Model("AllDifferent Example");

		// Create variables (domains: 1 to 3)
		IntVar x = model.intVar("X", 1, 3);
		IntVar y = model.intVar("Y", 1, 3);
		IntVar z = model.intVar("Z", 1, 3);

		// Add constraint: all variables must have different values
		model.allDifferent(x, y, z).post();

		// Solve the problem
		List<Solution> solutions = model.getSolver().findAllSolutions(null);

		if (solutions == null || solutions.isEmpty()) {
			System.out.println("No Solution Found....");
			return;
		}

		for (Solution solution : solutions) {
			System.out.println(solution.toString());
		}

	}
}
