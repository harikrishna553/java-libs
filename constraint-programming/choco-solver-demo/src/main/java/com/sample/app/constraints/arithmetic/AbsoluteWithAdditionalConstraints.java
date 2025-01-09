package com.sample.app.constraints.arithmetic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class AbsoluteWithAdditionalConstraints {
	public static void main(String[] args) {
		Model model = new Model("Absolute Constraint with Additional Relationship");

		// Define variables
		IntVar x = model.intVar("x", 0, 10); // x is non-negative
		IntVar y = model.intVar("y", -10, 10); // y can be negative or positive

		// Add absolute constraint: x = |y|
		model.absolute(x, y).post();

		// Add additional constraint: x + y = 4
		model.arithm(x, "+", y, "=", 4).post();

		// Solve and print solutions
		while (model.getSolver().solve()) {
			System.out.println("Solution: x = " + x.getValue() + ", y = " + y.getValue());
		}
	}
}
