package com.sample.app.constraints.arithmetic;

import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;

public class AllDifferentExample {
	public static void main(String[] args) {
		Model model = new Model("AllDifferent Constraint Example");

		// Define an array of variables
		IntVar[] vars = model.intVarArray("X", 3, 1, 5); // 3 variables with domains [1, 5]

		// Post the AllDifferent constraint
		model.allDifferent(vars).post();

		List<Solution> solutions = model.getSolver().findAllSolutions(null);

		System.out.println("Total Solutions found : " + solutions.size());
		int i = 1;
		for (Solution solution : solutions) {
			System.out.println("Solution " + (i++));

			System.out.print("X[0] = " + solution.getIntVal(vars[0]));
			System.out.print(", X[1] = " + solution.getIntVal(vars[1]));
			System.out.print(", X[2] = " + solution.getIntVal(vars[2]) + "\n");

		}

	}
}
