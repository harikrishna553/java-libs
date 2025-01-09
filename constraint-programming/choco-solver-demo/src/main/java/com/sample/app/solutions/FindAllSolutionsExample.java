package com.sample.app.solutions;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;

import java.util.List;

public class FindAllSolutionsExample {
    public static void main(String[] args) {
        Model model = new Model("Find All Solutions Example");
        IntVar x = model.intVar("x", 0, 5);  // Variable x in range [0, 5]
        IntVar y = model.intVar("y", 0, 5);  // Variable y in range [0, 5]

        // Constraint: x + y = 5
        model.arithm(x, "+", y, "=", 5).post();

        List<Solution> solutions = model.getSolver().findAllSolutions();
        if (solutions.isEmpty()) {
            System.out.println("No solutions found.");
        } else {
            System.out.println("All solutions:");
            for (Solution solution : solutions) {
                System.out.println("x = " + solution.getIntVal(x) + ", y = " + solution.getIntVal(y));
            }
        }
    }
}
