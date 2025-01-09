package com.sample.app.solutions;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;

import java.util.stream.Stream;

public class StreamSolutionsExample {
	public static void main(String[] args) {
		Model model = new Model("Stream Solutions Example");
		IntVar x = model.intVar("x", 0, 5); // Variable x in range [0, 5]
		IntVar y = model.intVar("y", 0, 5); // Variable y in range [0, 5]

		// Constraint: x + y = 5
		model.arithm(x, "+", y, "=", 5).post();

		Stream<Solution> solutionsStream = model.getSolver().streamSolutions();
		System.out.println("All solutions (streamed):");
		solutionsStream.forEach(
				solution -> System.out.println("x = " + solution.getIntVal(x) + ", y = " + solution.getIntVal(y)));
	}
}
