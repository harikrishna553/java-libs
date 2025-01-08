package com.sample.app.variables;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.RealVar;

public class RealVarExample {
	public static void main(String[] args) {
		// Create a model
		Model model = new Model("Real Variable Operations Example");

		// Define the precision parameter
		double p = 0.01d;

		// Declare real variables x and y with bounds [1, 5] and precision p
		RealVar x = model.realVar("x", 1, 5, p);
		RealVar y = model.realVar("y", 1, 5, p);

		// Perform the operation z = x^(y - 2)
		RealVar z = x.pow(y.sub(2)).realVar(p);

		// Print bounds and precision of x, y, and z
		System.out.println("Lower Bound of x: " + x.getLB());
		System.out.println("Upper Bound of x: " + x.getUB());
		System.out.println("Lower Bound of y: " + y.getLB());
		System.out.println("Upper Bound of y: " + y.getUB());
		System.out.println("Lower Bound of z: " + z.getLB());
		System.out.println("Upper Bound of z: " + z.getUB());

	}
}
