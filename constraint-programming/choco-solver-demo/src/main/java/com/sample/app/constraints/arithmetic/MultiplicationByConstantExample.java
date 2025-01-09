package com.sample.app.constraints.arithmetic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class MultiplicationByConstantExample {
    public static void main(String[] args) {
        Model model = new Model("Multiplication by Constant");

        // Define variables
        IntVar x = model.intVar("x", 1, 5); // x in [1, 5]
        IntVar z = model.intVar("z", 1, 15); // z in [1, 15]

        // Add multiplication constraint: x * 3 = z
        model.times(x, 3, z).post();

        // Solve and print solutions
        while (model.getSolver().solve()) {
            System.out.println("Solution: x = " + x.getValue() + ", z = " + z.getValue());
        }
    }
}
