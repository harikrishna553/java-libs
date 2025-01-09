package com.sample.app.constraints.arithmetic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class DivisionExample {
	public static void main(String[] args) {
		Model model = new Model("Simple Division");

		// Define variables
		IntVar x = model.intVar("x", -5, 5); // x in [-5, 5]
		IntVar y = model.intVar("y", 2, 4); // y in [2, 4] (non-zero divisor)
		IntVar z = model.intVar("z", -5, 5); // z in [-5, 5]

		// Add division constraint: x / y = z
		model.div(x, y, z).post();

		// Solve and print solutions
		while (model.getSolver().solve()) {
			System.out.println("Solution: x = " + x.getValue() + ", y = " + y.getValue() + ", z = " + z.getValue());
		}
	}
}
