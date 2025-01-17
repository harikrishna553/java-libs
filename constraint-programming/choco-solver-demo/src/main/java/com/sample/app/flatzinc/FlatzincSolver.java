package com.sample.app.flatzinc;

import org.chocosolver.parser.flatzinc.Flatzinc;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.RealVar;
import org.chocosolver.solver.variables.SetVar;
import org.chocosolver.solver.variables.Variable;
import org.chocosolver.solver.search.SearchState;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class FlatzincSolver {

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
	public static Map<String, Object> solveFlatzinc() {
		Map<String, Object> solutionMap = new HashMap<>();

		try {
			// Create a model for the solver
			Model model = new Model("Flatzinc Problem");

			// Use the Flatzinc class to parse the problem
			Flatzinc fzn = new Flatzinc();

			String file = resourceFilePath("example.fzn");

			// Set up the Flatzinc parser (using a String as input content)
			String[] args = new String[] { file};
			fzn.setUp(args);

			// Create solver and build the model
			fzn.createSolver();
			fzn.buildModel();
			fzn.configureSearch();

			// Solve the problem
			if (fzn.getModel().getSolver().solve()) {
				// Extract the solution as a map
				solutionMap = getVariableValues(fzn.getModel());

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
		System.out.println(solveFlatzinc());
	}
}
