package com.sample.app.constraints.reify;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;

public class ReifyExample {
    public static void main(String[] args) {
        // Create a model
        Model model = new Model("Reify Example");

        // Create variables
        IntVar x = model.intVar("X", -5, 5); // X can be between -5 and 5
        BoolVar b = model.arithm(x, "<", 0).reify(); // Reify the constraint x < 0

        // Solve the problem
        while (model.getSolver().solve()) {
            System.out.println("X = " + x.getValue() + ", B = " + b.getValue());
        }
    }
}
