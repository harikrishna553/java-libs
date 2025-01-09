package com.sample.app.solutions;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

public class OptimizationExample {
	public static void main(String[] args) {
		Model maximizeModel = new Model("Maximization");
		IntVar x1 = maximizeModel.intVar("x", 0, 10);
		IntVar y1 = maximizeModel.intVar("y", 0, 10);
		maximizeModel.arithm(x1, "+", y1, "=", 15).post();
		maximizeModel.setObjective(Model.MAXIMIZE, x1);
		Solver maximizeSolver = maximizeModel.getSolver();

		System.out.println("Maximizing x + y = 15");
		while (maximizeSolver.solve()) {
			System.out.println("x = " + x1.getValue() + ", y = " + y1.getValue());
		}

		Model minimizeModel = new Model("Minimization");
		IntVar x2 = minimizeModel.intVar("x", 0, 10);
		IntVar y2 = minimizeModel.intVar("y", 0, 10);
		minimizeModel.arithm(x2, "+", y2, "=", 15).post();
		minimizeModel.setObjective(Model.MINIMIZE, x2);
		Solver minimizeSolver = minimizeModel.getSolver();

		System.out.println("\nMinimizing x + y = 15");
		while (minimizeSolver.solve()) {
			System.out.println("x = " + x2.getValue() + ", y = " + y2.getValue());
		}
	}
}
