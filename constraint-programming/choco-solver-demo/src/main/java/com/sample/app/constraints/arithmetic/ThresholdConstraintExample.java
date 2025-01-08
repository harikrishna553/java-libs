package com.sample.app.constraints.arithmetic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class ThresholdConstraintExample {
    public static void main(String[] args) {
        Model model = new Model("Threshold Constraint");

        // Define an integer variable 'x' with a domain [0, 10]
        IntVar x = model.intVar("x", 0, 10);

        // Add the constraint: x >= 3
        model.arithm(x, ">=", 3).post();

        // Solve and print the solution
        if (model.getSolver().solve()) {
            System.out.println("Solution: x = " + x.getValue());
        } else {
            System.out.println("No solution found.");
        }
    }
}
