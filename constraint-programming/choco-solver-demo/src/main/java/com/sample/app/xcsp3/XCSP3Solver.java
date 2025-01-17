package com.sample.app.xcsp3;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.chocosolver.parser.xcsp.XCSP;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.RealVar;
import org.chocosolver.solver.variables.SetVar;
import org.chocosolver.solver.variables.Variable;

import com.sample.app.flatzinc.FlatzincSolver;

public class XCSP3Solver {

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

	public static String resourceFilePath(String resourceFile) {
		ClassLoader classLoader = FlatzincSolver.class.getClassLoader();
		URL url = classLoader.getResource(resourceFile);
		return url.getPath();
	}

	/**
	 * Solves a Flatzinc problem from a given Flatzinc content and returns the
	 * solution as a map.
	 *
	 * @param flatzincContent The content of the Flatzinc problem as a string.
	 * @return A map containing the variable names as keys and their values as the
	 *         solution.
	 */
	public static Map<String, Object> solveXcsp3() {
		Map<String, Object> solutionMap = new HashMap<>();

		try {

			String file = resourceFilePath("xcsp3.xml");

			String[] args = new String[] { file, "-limit", "110s" };

			XCSP xscp = new XCSP();
			xscp.setUp(args);
			xscp.createSolver();
			xscp.buildModel();
			xscp.configureSearch();

			// Solve the problem
			if (xscp.getModel().getSolver().solve()) {
				return getVariableValues(xscp.getModel());

			} else {
				// Handle case where no solution is found
				System.out.println("No solution found.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error solving Flatzinc problem: " + e.getMessage());
		}

		return solutionMap;
	}

	public static void main(String[] args) {
		System.out.println("Data : " + solveXcsp3());
	}
}
