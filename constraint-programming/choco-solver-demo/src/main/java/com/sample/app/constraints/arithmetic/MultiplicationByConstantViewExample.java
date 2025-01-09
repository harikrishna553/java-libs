package com.sample.app.constraints.arithmetic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class MultiplicationByConstantViewExample {
    public static void main(String[] args) {
        Model model = new Model("Multiplication by Constant with View");

        // Define variable
        IntVar x = model.intVar("x", 1, 5); // x in [1, 5]

        // Use a view to define z as x * 3
        IntVar z = model.intScaleView(x, 3);

        // Solve and print solutions
        while (model.getSolver().solve()) {
            System.out.println("Solution: x = " + x.getValue() + ", z = " + z.getValue());
        }
    }
}
