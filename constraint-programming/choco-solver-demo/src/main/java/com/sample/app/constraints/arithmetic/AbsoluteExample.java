package com.sample.app.constraints.arithmetic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class AbsoluteExample {
    public static void main(String[] args) {
        Model model = new Model("Absolute Constraint");

        // Define variables
        IntVar x = model.intVar("x", 0, 10);  // x is non-negative
        IntVar y = model.intVar("y", -10, 10); // y can be negative or positive

        // Add absolute constraint: x = |y|
        model.absolute(x, y).post();

        // Solve and print solutions
        while (model.getSolver().solve()) {
            System.out.println("Solution: x = " + x.getValue() + ", y = " + y.getValue());
        }
    }
}
