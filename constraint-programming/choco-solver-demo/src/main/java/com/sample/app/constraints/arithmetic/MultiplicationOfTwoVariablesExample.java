package com.sample.app.constraints.arithmetic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class MultiplicationOfTwoVariablesExample {
	public static void main(String[] args) {
		Model model = new Model("Multiplication of Two Variables");

		// Define variables
		IntVar x = model.intVar("x", 1, 5); // x in [1, 5]
		IntVar y = model.intVar("y", 1, 5); // y in [1, 5]
		IntVar z = model.intVar("z", 1, 25); // z in [1, 25]

		// Add multiplication constraint: x * y = z
		model.times(x, y, z).post();

		// Solve and print solutions
		while (model.getSolver().solve()) {
			System.out.println("Solution: x = " + x.getValue() + ", y = " + y.getValue() + ", z = " + z.getValue());
		}
	}
}
