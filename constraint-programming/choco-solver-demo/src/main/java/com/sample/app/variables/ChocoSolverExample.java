package com.sample.app.variables;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class ChocoSolverExample {

	public static void main(String[] args) {
		// Step 1: Create an instance of the Model class
		Model model = new Model("Example Problem");

		// Step 2: Declare integer variables

		// 1. Constant variable equal to 42
		IntVar constantVar = model.intVar("constantVar", 42);
		System.out.println("Constant Variable: " + constantVar);

		// 2. Bounded variable taking values in the range [1, 5]
		IntVar boundedVar = model.intVar("boundedVar", 1, 5);
		System.out.println("Bounded Variable: " + boundedVar);

		// 3. Enumerated variable taking values in {2, 4, 6}
		IntVar enumeratedVar = model.intVar("enumeratedVar", new int[] { 2, 4, 6 });
		System.out.println("Enumerated Variable: " + enumeratedVar);

		// Step 3: Declare arrays and matrices of variables

		// 1. Array of 3 variables with values in [-1, 1]
		IntVar[] varArray = model.intVarArray("varArray", 3, -1, 1);
		System.out.println("Variable Array:");
		for (IntVar var : varArray) {
			System.out.println("  " + var);
		}

		// 2. Matrix of 2x2 variables with values in [0, 9]
		IntVar[][] varMatrix = model.intVarMatrix("varMatrix", 2, 2, 0, 9);
		System.out.println("Variable Matrix:");
		for (int i = 0; i < varMatrix.length; i++) {
			for (int j = 0; j < varMatrix[i].length; j++) {
				System.out.print(varMatrix[i][j] + " ");
			}
			System.out.println();
		}

		// Step 4: Add a simple constraint
		// Example: The value of boundedVar must be equal to the value of enumeratedVar
		model.arithm(boundedVar, "=", enumeratedVar).post();

		// Step 5: Solve the problem
		if (model.getSolver().solve()) {
			System.out.println("\nSolution found:");
			System.out.println("  Bounded Variable Value: " + boundedVar.getValue());
			System.out.println("  Enumerated Variable Value: " + enumeratedVar.getValue());
		} else {
			System.out.println("\nNo solution found.");
		}
	}
}
