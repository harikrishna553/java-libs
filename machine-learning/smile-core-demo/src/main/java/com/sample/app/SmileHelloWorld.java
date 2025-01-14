package com.sample.app;

import smile.plot.swing.ScatterPlot;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class SmileHelloWorld {
	public static void main(String[] args) throws InvocationTargetException, InterruptedException {
		// Generate random data points
		int n = 100; // Number of points
		double[][] data = new double[n][2];
		Random random = new Random();

		for (int i = 0; i < n; i++) {
			data[i][0] = random.nextDouble() * 10; // X-coordinate
			data[i][1] = random.nextDouble() * 10; // Y-coordinate
		}

		// Create a scatter plot
		ScatterPlot plot = ScatterPlot.of(data);
		plot.canvas().window(); // Display the plot in a window

		System.out.println("Hello Smile! The scatter plot is displayed.");
	}
}
