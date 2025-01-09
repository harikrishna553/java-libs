package com.sample.app.solutions;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import java.util.List;

public class MultiObjectiveOptimization {
    public static void main(String[] args) {
        // Create a model
        Model model = new Model("Multi-Objective Optimization");

        // Define variables
        IntVar a = model.intVar("a", 0, 2); // Objective variable a
        IntVar b = model.intVar("b", 0, 5); // Objective variable b
        IntVar c = model.intVar("c", 0, 5); // Helper variable c

        // Constraint: a + b = c
        model.arithm(a, "+", b, "=", c).post();

        // Create a solver
        Solver solver = model.getSolver();

        // Find the Pareto Front for objectives a and b
		List<Solution> paretoFront = solver.findParetoFront(new IntVar[] { a, b }, Model.MAXIMIZE);

        // Display the Pareto Front
        System.out.println("The Pareto Front contains " + paretoFront.size() + " solutions:");
        for (Solution solution : paretoFront) {
            System.out.println("a = " + solution.getIntVal(a) + ", b = " + solution.getIntVal(b));
        }
    }
}

