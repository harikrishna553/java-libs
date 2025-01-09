package com.sample.app.util;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.*;
import java.util.HashMap;
import java.util.Map;

public class ReadSolutionDynamically {
	// Generic method to get all variable values from a model
	public static Map<String, Object> getVariableValues(Model model) {
		Map<String, Object> variableValues = new HashMap<>();

		// Loop through all variables in the model
		for (Variable var : model.getVars()) {
			// Check the type of the variable and extract its value accordingly
			if (var instanceof IntVar) {
				IntVar intVar = (IntVar) var;
				variableValues.put(intVar.getName(), intVar.getValue());
			} else if (var instanceof RealVar) {
				RealVar realVar = (RealVar) var;
				variableValues.put(realVar.getName(), realVar.getLB()); // Use lower bound for RealVar
			} else if (var instanceof SetVar) {
				SetVar setVar = (SetVar) var;
				variableValues.put(setVar.getName(), setVar.getValue());
			} else {
				// Handle other variable types if needed
				variableValues.put(var.getName(), "Unsupported Variable Type");
			}
		}

		return variableValues;
	}

	public static void main(String[] args) {
		// Create a new model
		Model model = new Model("Limit By Time Example");

		// Define variables with ranges
		IntVar x = model.intVar("x", 0, 10); // Variable x in range [0, 10]
		IntVar y = model.intVar("y", 0, 10); // Variable y in range [0, 10]

		model.arithm(x, "+", y, "=", 5).post();

		Solver solver = model.getSolver();

		while (solver.solve()) {
			Map<String, Object> solutionMap = getVariableValues(model);
			System.out.println(solutionMap);
		}

	}

}
