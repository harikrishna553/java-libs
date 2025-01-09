package com.sample.app.propagation;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

public class PropagationExample {
    public static void main(String[] args) {
        // Create a new model
        Model model = new Model("Propagation Example");

        // Define variables with ranges
        IntVar x = model.intVar("x", 0, 10);  // Variable x in range [0, 10]
        IntVar y = model.intVar("y", 0, 10);  // Variable y in range [0, 10]

        // Apply a constraint: x + y = 10
        model.arithm(x, "+", y, "=", 5).post();

        // Propagate the constraints to reduce domains
        try {
            // Propagate constraints (domain reduction)
            model.getSolver().propagate();

            // Check if the propagation was successful and print the reduced domains
            System.out.println("After propagation:");
            System.out.println("Domain of x: " + x);
            System.out.println("Domain of y: " + y);
        } catch (Exception e) {
            // If a contradiction occurs during propagation, handle it
            System.out.println("Contradiction found! No solution.");
            model.getSolver().getEngine().flush(); // Flush the engine to clear pending events
        }

        // Now solve the problem
        Solver solver = model.getSolver();
        while (solver.solve()) {
            System.out.println("Solution: x = " + x.getValue() + ", y = " + y.getValue());
        }
    }
}
