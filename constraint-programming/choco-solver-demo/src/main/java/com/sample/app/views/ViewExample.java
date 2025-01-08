package com.sample.app.views;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class ViewExample {
	public static void main(String[] args) {
		// Create a model
		Model model = new Model("View Example");

		// Create an integer variable y with values between 0 and 10
		IntVar y = model.intVar("y", 0, 10);

		// Arithmetic view: x = y + 10
		IntVar x = model.intView(1, y, 10);

		// Add a constraint: y should be between 3 and 7
		model.arithm(y, ">=", 3).post();
		model.arithm(y, "<=", 7).post();

		// Solve the model and print all solutions
		int solutionCount = 0;
		while (model.getSolver().solve()) {
			solutionCount++;
			// Output the solution
			System.out.println("Solution " + solutionCount + ":");
			System.out.println("y = " + y.getValue());
			System.out.println("x = " + x.getValue());
			System.out.println();
		}

		if (solutionCount == 0) {
			System.out.println("No solution found.");
		} else {
			System.out.println("Total number of solutions found: " + solutionCount);
		}
	}
}
