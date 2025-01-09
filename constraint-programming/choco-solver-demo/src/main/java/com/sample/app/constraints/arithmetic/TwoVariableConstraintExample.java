package com.sample.app.constraints.arithmetic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class TwoVariableConstraintExample {
    public static void main(String[] args) {
        Model model = new Model("Two Variables Constraint");

        // Define integer variables 'x' and 'y' with domains [0, 5]
        IntVar x = model.intVar("x", 0, 5);
        IntVar y = model.intVar("y", 0, 5);

        // Add the constraint: x + y <= 4
        model.arithm(x, "+", y, "<=", 4).post();

        // Solve and print all solutions
        while (model.getSolver().solve()) {
            System.out.println("x = " + x.getValue() + ", y = " + y.getValue());
        }
    }
}
