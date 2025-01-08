package com.sample.app.constraints;

import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;

public class SumConstraintExample {
	public static void main(String[] args) {
		// Create a model
		Model model = new Model("Sum Constraint Example");

		// Create variables (domains: 1 to 5)
		IntVar x = model.intVar("X", 1, 5);
		IntVar y = model.intVar("Y", 1, 5);
		IntVar z = model.intVar("Z", 1, 5);

		// Add constraint: x + y + z = 10
		model.sum(new IntVar[] { x, y, z }, "=", 10).post();

		// Solve the problem
		List<Solution> solutions = model.getSolver().findAllSolutions(null);

		if (solutions == null || solutions.isEmpty()) {
			System.out.println("No Solution Found....");
			return;
		}

		System.out.println("Total Solutions Found " + solutions.size());

		for (Solution solution : solutions) {
			System.out.println(solution.toString());
		}
	}
}
