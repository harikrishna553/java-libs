package com.sample.app.constraints.arithmetic;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;

public class Sudoku9x9Example {
	public static void main(String[] args) {
		Model model = new Model("9x9 Sudoku Solver");

		// Define a 9x9 Sudoku grid with variables ranging from 1 to 9
		IntVar[][] grid = new IntVar[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				grid[i][j] = model.intVar("cell_" + i + "_" + j, 1, 9);
			}
		}

		// Apply AllDifferent constraint on rows
		for (int i = 0; i < 9; i++) {
			model.allDifferent(grid[i]).post();
		}

		// Apply AllDifferent constraint on columns
		for (int j = 0; j < 9; j++) {
			IntVar[] column = new IntVar[9];
			for (int i = 0; i < 9; i++) {
				column[i] = grid[i][j];
			}
			model.allDifferent(column).post();
		}

		// Some custom constraint, like diagonal elements like 1, 2, 3....9
		for (int i = 0; i < 9; i++) {
			model.arithm(grid[i][i], "=", i + 1).post();
		}

		// Apply AllDifferent constraint on 3x3 subgrids
		for (int rowBlock = 0; rowBlock < 3; rowBlock++) {
			for (int colBlock = 0; colBlock < 3; colBlock++) {
				IntVar[] subgrid = new IntVar[9];
				int index = 0;
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						subgrid[index++] = grid[rowBlock * 3 + i][colBlock * 3 + j];
					}
				}
				model.allDifferent(subgrid).post();
			}
		}

		// Solve and print solutions
		if (model.getSolver().solve()) {
			System.out.println("Solved Sudoku:");
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					System.out.print(grid[i][j].getValue() + " ");
				}
				System.out.println();
			}
		} else {
			System.out.println("No solution found!");
		}
		
	}
}
