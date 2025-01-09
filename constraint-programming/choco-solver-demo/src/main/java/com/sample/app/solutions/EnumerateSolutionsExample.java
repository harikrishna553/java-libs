package com.sample.app.solutions;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

public class EnumerateSolutionsExample {
	public static void main(String[] args) {
		Model model = new Model("Enumerate Solutions Example");
		IntVar x = model.intVar("x", 0, 5); // Variable x in range [0, 5]
		IntVar y = model.intVar("y", 0, 5); // Variable y in range [0, 5]

		// Constraint: x + y = 5
		model.arithm(x, "+", y, "=", 5).post();

		Solver solver = model.getSolver();
		System.out.println("All solutions:");
		while (solver.solve()) {
			System.out.println("x = " + x.getValue() + ", y = " + y.getValue());
		}
	}
}
