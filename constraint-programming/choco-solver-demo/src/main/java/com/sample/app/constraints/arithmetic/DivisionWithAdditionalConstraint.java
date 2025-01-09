package com.sample.app.constraints.arithmetic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class DivisionWithAdditionalConstraint {
    public static void main(String[] args) {
        Model model = new Model("Division with Additional Constraint");

        // Define variables
        IntVar x = model.intVar("x", 1, 20);  // x in [1, 20]
        IntVar y = model.intVar("y", 1, 4);   // y in [1, 4] (non-zero divisor)
        IntVar z = model.intVar("z", 1, 5);   // z in [1, 5]

        // Add division constraint: x / y = z
        model.div(x, y, z).post();

        // Add additional constraint: z + y = 5
        model.arithm(z, "+", y, "=", 5).post();

        // Solve and print solutions
        while (model.getSolver().solve()) {
            System.out.println("Solution: x = " + x.getValue() + ", y = " + y.getValue() + ", z = " + z.getValue());
        }
    }
}
