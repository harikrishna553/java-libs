package com.sample.app.solutions;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.objective.IObjectiveManager;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.Variable;

public class CustomCutExample {
	public static void main(String[] args) {
		// Create a new model
		Model model = new Model("Custom Cut Example");
		IntVar x = model.intVar("x", 0, 50); // Variable x in range [0, 50]
		IntVar y = model.intVar("y", 0, 50); // Variable y in range [0, 50]

		// Constraint: x + y <= 70
		model.arithm(x, "+", y, "<=", 100).post();

		// Objective: Maximize x + y
		IntVar objective = x.add(y).intVar();
		model.setObjective(Model.MAXIMIZE, x);

		// Get the solver and customize the cut behavior
		Solver solver = model.getSolver();
		IObjectiveManager<Variable> oman = solver.getObjectiveManager();

		// Custom cut: Require the next solution to have an objective value >= current
		// best + 10
		oman.setCutComputer(bestValue -> {
			return bestValue.intValue() + 10;
		});

		System.out.println("Maximizing with Custom Cut:");
		while (solver.solve()) {
			System.out
					.println("x = " + x.getValue() + ", y = " + y.getValue() + ", Objective = " + objective.getValue());
		}
	}
}
