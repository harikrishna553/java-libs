package com.sample.app.tools;

import dev.langchain4j.agent.tool.Tool;

/**
 * Example tool class demonstrating mathematical operations with the @Tool annotation.
 * 
 * This class shows how LangChain4J automatically generates tool specifications
 * from annotated methods, including parameter types, descriptions, and validation.
 */
public class CalculatorTool {
    
    @Tool("Add two numbers together")
    public double add(
        @dev.langchain4j.agent.tool.P("The first number to add") double a,
        @dev.langchain4j.agent.tool.P("The second number to add") double b
    ) {
        double result = a + b;
        System.out.println("Executing add(" + a + ", " + b + ") = " + result);
        return result;
    }
    
    @Tool("Subtract the second number from the first number")
    public double subtract(
        @dev.langchain4j.agent.tool.P("The number to subtract from") double minuend,
        @dev.langchain4j.agent.tool.P("The number to subtract") double subtrahend
    ) {
        double result = minuend - subtrahend;
        System.out.println("Executing subtract(" + minuend + ", " + subtrahend + ") = " + result);
        return result;
    }
    
    @Tool("Multiply two numbers together")
    public double multiply(
        @dev.langchain4j.agent.tool.P("The first number to multiply") double a,
        @dev.langchain4j.agent.tool.P("The second number to multiply") double b
    ) {
        double result = a * b;
        System.out.println("Executing multiply(" + a + ", " + b + ") = " + result);
        return result;
    }
    
    @Tool("Divide the first number by the second number")
    public double divide(
        @dev.langchain4j.agent.tool.P("The dividend (number to be divided)") double dividend,
        @dev.langchain4j.agent.tool.P("The divisor (number to divide by)") double divisor
    ) {
        if (divisor == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        double result = dividend / divisor;
        System.out.println("Executing divide(" + dividend + ", " + divisor + ") = " + result);
        return result;
    }
    
    @Tool("Calculate the power of a number (base raised to the exponent)")
    public double power(
        @dev.langchain4j.agent.tool.P("The base number") double base,
        @dev.langchain4j.agent.tool.P("The exponent") double exponent
    ) {
        double result = Math.pow(base, exponent);
        System.out.println("Executing power(" + base + ", " + exponent + ") = " + result);
        return result;
    }
    
    @Tool("Calculate the square root of a number")
    public double sqrt(
        @dev.langchain4j.agent.tool.P("The number to calculate square root of (must be non-negative)") double number
    ) {
        if (number < 0) {
            throw new IllegalArgumentException("Cannot calculate square root of negative number");
        }
        double result = Math.sqrt(number);
        System.out.println("Executing sqrt(" + number + ") = " + result);
        return result;
    }
}
