package com.sample.app.tools;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.model.output.structured.Description;

@Description("All the tools related to mathematical operations are found here")
public class MathTools {

	@Tool("""
			Calculate the square root of a number, and return the result as a double value.
			""")
	public Double squareRoot(double number) {
		System.out.println("***Calculating the Square Root Of A Number***");
		return Math.sqrt(number);
	}

	@Tool("""
			Take radians as input and return the equivalant value in degrees
			""")
	public Double radiansToDegrees(double radians) {
		System.out.println("***Converting Radians to Degrees***");
		return Math.toDegrees(radians);
	}

	@Tool("""
			Take degress as input and return the equivalant value in radians
			""")
	public Double degreesToRadians(double degrees) {
		System.out.println("***Converting Degrees to Radians***");
		return Math.toRadians(degrees);
	}

}
