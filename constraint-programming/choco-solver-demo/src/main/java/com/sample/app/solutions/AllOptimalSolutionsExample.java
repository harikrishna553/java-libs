package com.sample.app.solutions;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;

import java.util.List;

public class AllOptimalSolutionsExample {
	public static void main(String[] args) {
		Model model = new Model("All Optimal Solutions Example");
		IntVar x = model.intVar("x", 0, 10); // Variable x in range [0, 10]
		IntVar y = model.intVar("y", 0, 10); // Variable y in range [0, 10]

		// Constraint: x + y <= 12
		model.arithm(x, "+", y, "<=", 12).post();

		// Find all optimal solutions for maximizing x
		List<Solution> solutions = model.getSolver().findAllOptimalSolutions(x, Model.MAXIMIZE);

		if (!solutions.isEmpty()) {
			System.out.println("All optimal solutions:");
			for (Solution solution : solutions) {
				System.out.println("x = " + solution.getIntVal(x) + ", y = " + solution.getIntVal(y));
			}
		} else {
			System.out.println("No optimal solutions found.");
		}
	}
}
