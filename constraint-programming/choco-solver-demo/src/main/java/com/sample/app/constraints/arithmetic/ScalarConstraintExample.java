package com.sample.app.constraints.arithmetic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class ScalarConstraintExample {
    public static void main(String[] args) {
        Model model = new Model("Scalar Constraint Example");

        // Define variables
        IntVar[] vars = new IntVar[] {
            model.intVar("v1", 1, 5),
            model.intVar("v2", 2, 6),
            model.intVar("v3", 3, 7)
        };

        // Define coefficients
        int[] coefs = {2, 3, 4}; // Weights for v1, v2, v3 respectively

        // Define target
        IntVar target = model.intVar("target", 20); // Target weighted sum

        // Post scalar constraint: 2*v1 + 3*v2 + 4*v3 = target
        model.scalar(vars, coefs, "=", target).post();

        // Solve and print solutions
        while (model.getSolver().solve()) {
            System.out.println("Solution:");
            for (IntVar var : vars) {
                System.out.println(var.getName() + " = " + var.getValue());
            }
            System.out.println("Target Weighted Sum = " + target.getValue());
        }
    }
}
