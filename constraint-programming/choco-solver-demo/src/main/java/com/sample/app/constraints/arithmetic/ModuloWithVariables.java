package com.sample.app.constraints.arithmetic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class ModuloWithVariables {
	public static void main(String[] args) {
		Model model = new Model("Modulo with Variables");

		// Define variables
		IntVar x = model.intVar("x", 1, 10); // x in [1, 10]
		IntVar y = model.intVar("y", 1, 5); // y in [1, 5] (non-zero divisor)
		IntVar z = model.intVar("z", 0, 2); // z in [0, 2] (remainder)

		// Add modulo constraint: x % y = z
		model.mod(x, y, z).post();

		// Solve and print solutions
		while (model.getSolver().solve()) {
			System.out.println("Solution: x = " + x.getValue() + ", y = " + y.getValue() + ", z = " + z.getValue());
		}
	}
}
