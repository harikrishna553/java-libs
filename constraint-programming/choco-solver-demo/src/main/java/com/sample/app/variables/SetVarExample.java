package com.sample.app.variables;

import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.SetVar;

public class SetVarExample {
	public static void main(String[] args) {
		// Step 1: Create a model
		Model model = new Model("Set Variables Example");

		// Step 2: Define set variables with lower and upper bounds
		SetVar x = model.setVar("x", new int[] { 2, 3, 12, 14 });
		SetVar y = model.setVar("y", new int[] {}, new int[] { 1, 2, 3, 5, 12, 14 });
		SetVar z = model.setVar("z", new int[] { 2, 3 }, new int[] { 1, 2, 3, 5, 12, 14, 16 });

		// Step 3: Add constraints
		// Set y is a subset of set x.
		model.subsetEq(y, x).post();

		// set x is a subset of set z
		model.subsetEq(x, z).post();

		List<Solution> solutions = model.getSolver().findAllSolutions();
		System.out.println("Total Solutions found : " + solutions.size());
		int i = 1;
		for (Solution solution : solutions) {
			System.out.println("Solution " + (i++));
			int[] xSolutions = solution.getSetVal(x);
			int[] ySolutions = solution.getSetVal(y);
			int[] zSolutions = solution.getSetVal(z);

			System.out.println("Set x: " + printSet(xSolutions));
			System.out.println("Set y: " + printSet(ySolutions));
			System.out.println("Set z: " + printSet(zSolutions) + "\n");
		}

	}

	private static String printSet(int[] values) {

		if (values == null) {
			return "No values in the set";
		}

		StringBuilder sb = new StringBuilder("{");
		for (int i = 0; i < values.length; i++) {
			sb.append(values[i]);
			if (i < values.length - 1) {
				sb.append(", ");
			}
		}
		sb.append("}");
		return sb.toString();
	}
}
