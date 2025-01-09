package com.sample.app.constraints.arithmetic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class MinMaxExample {
	public static void main(String[] args) {
		Model model = new Model("Min and Max Constraints");

		IntVar v1 = model.intVar("v1", 1, 10);
		IntVar v2 = model.intVar("v2", 2, 20);
		IntVar v3 = model.intVar("v3", 5, 15);

		// Define variables
		IntVar[] vars = new IntVar[] { v1, v2, v3 };

		IntVar min = model.intVar("min", 0, 10); // Variable to hold the minimum
		IntVar max = model.intVar("max", 0, 10); // Variable to hold the maximum

		// Post minimum and maximum constraints
		model.min(min, vars).post();
		model.max(max, vars).post();

		model.arithm(v1, "+", v2, "=", v3).post(); // v1 + v2 = v3
		model.arithm(v1, "=", model.intScaleView(v2, 2)).post(); // v1 = 2 * v2

		// Solve and print solutions
		while (model.getSolver().solve()) {
			System.out.println("\nSolution: ");
			for (IntVar var : vars) {
				System.out.println(var.getName() + " = " + var.getValue());
			}
			System.out.println("Min = " + min.getValue());
			System.out.println("Max = " + max.getValue());
		}
	}
}
