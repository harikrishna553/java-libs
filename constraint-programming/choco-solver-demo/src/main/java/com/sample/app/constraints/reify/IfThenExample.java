package com.sample.app.constraints.reify;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class IfThenExample {
    public static void main(String[] args) {
        // Create a model
        Model model = new Model("IfThen Example");

        // Create variables
        IntVar x = model.intVar("X", -2, 2); // X can be between -2 and 2
        IntVar y = model.intVar("Y", 40, 45); // Y can be between 40 and 45

        // Add conditional constraint: If X < 0, then Y > 42
        model.ifThen(
            model.arithm(x, "<", 0),   // Condition
            model.arithm(y, ">", 42)  // Action
        );

        // Solve the problem
        while (model.getSolver().solve()) {
            System.out.println("X = " + x.getValue() + ", Y = " + y.getValue());
        }
    }
}
