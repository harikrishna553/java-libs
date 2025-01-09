package com.sample.app.constraints.scheduling;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.Task;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.Task;

public class SimpleScheduling {
	public static void main(String[] args) {
        // Create a model
        Model model = new Model("Variable Duration Scheduling");

        // Define tasks with variable durations
        IntVar start1 = model.intVar("Start1", 0, 10);
        IntVar duration1 = model.intVar("Duration1", 2, 5); // Duration can be between 2 and 5
        IntVar end1 = model.intVar("End1", 2, 15);
        Task task1 = new Task(start1, duration1, end1);

        IntVar start2 = model.intVar("Start2", 0, 10);
        IntVar duration2 = model.intVar("Duration2", 3, 6); // Duration can be between 3 and 6
        IntVar end2 = model.intVar("End2", 3, 16);
        Task task2 = new Task(start2, duration2, end2);
        

        // Constraint: Task 2 must start after Task 1 ends
        model.arithm(start2, ">=", end1).post();

        // Solve the problem
        if (model.getSolver().solve()) {
            System.out.println("Task 1 starts at: " + start1.getValue() + ", lasts: " + duration1.getValue());
            System.out.println("Task 2 starts at: " + start2.getValue() + ", lasts: " + duration2.getValue());
        } else {
            System.out.println("No solution found.");
        }
    }
}
