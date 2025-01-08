package com.sample.app.variables;

import org.chocosolver.solver.Model;

public class HelloWorld {

	public static void main(String[] args) {

		/**
		 * Model is the top-level object that stores declared variables, posted
		 * constraints and gives access to the Solver. This should be the first
		 * instruction, prior to any other modeling instructions, as it is needed to
		 * declare variables and constraints.
		 * 
		 */
		Model model = new Model("My problem");

	}

}
