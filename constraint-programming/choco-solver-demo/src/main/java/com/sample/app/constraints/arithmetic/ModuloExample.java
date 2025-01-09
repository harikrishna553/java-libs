package com.sample.app.constraints.arithmetic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class ModuloExample {
	public static void main(String[] args) {
		Model model = new Model("Simple Modulo");

		// Define variables
		IntVar x = model.intVar("x", 0, 20); // x in [0, 20]
		int a = 5;
		int b = 2;

		// Add modulo constraint: x % a = b
		model.mod(x, a, b).post();

		// Solve and print solutions
		while (model.getSolver().solve()) {
			System.out.println("Solution: x = " + x.getValue() + ", b = " + b);
		}
	}
}
