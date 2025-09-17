package com.sample.app.tools;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ToolExecutionResultMessage;

/**
 * Complete example demonstrating the full LangChain4J tool execution flow:
 * 1. Tool definition with @Tool annotations
 * 2. Tool specification generation
 * 3. LLM receives tool specifications in ChatRequest
 * 4. LLM returns ToolExecutionRequest in AiMessage
 * 5. Tool execution and result wrapping
 */
public class CompleteToolExecutionExample {
    
    public static void main(String[] args) {
        System.out.println("=== Complete LangChain4J Tool Execution Flow Example ===\n");
        
        // Step 1: Create tool instances
        CalculatorTool calculatorTool = new CalculatorTool();
        WeatherTool weatherTool = new WeatherTool();
        
        // Step 2: Generate tool specifications (this is what gets sent to the LLM)
        List<ToolSpecification> toolSpecifications = ToolSpecifications.toolSpecificationsFrom(
            calculatorTool
        );
        
        System.out.println("Step 1: Tool Specifications Generated");
        System.out.println("====================================");
        System.out.println("These specifications are included in the ChatRequest sent to the LLM:");
        
        for (ToolSpecification spec : toolSpecifications) {
            System.out.println("\nTool: " + spec.name());
            System.out.println("Description: " + spec.description());
            System.out.println("Parameters Schema: " + spec.parameters());
        }
        
        // Step 3: Simulate LLM response with tool execution requests
        System.out.println("\n\nStep 2: Simulated LLM Response");
        System.out.println("==============================");
        System.out.println("The LLM analyzes the user query and decides to use tools...");
        
        // Simulate tool execution requests that would come from the LLM
        List<ToolExecutionRequest> toolRequests = simulateLLMToolRequests();
        
        // Create AiMessage with tool execution requests (as LLM would return)
        AiMessage aiMessage = AiMessage.from(toolRequests);
        System.out.println("LLM returned AiMessage with " + aiMessage.toolExecutionRequests().size() + " tool requests");
        
        // Step 4: Execute tools and generate results
        System.out.println("\n\nStep 3: Tool Execution");
        System.out.println("======================");
        
        for (ToolExecutionRequest request : aiMessage.toolExecutionRequests()) {
            System.out.println("\nExecuting tool: " + request.name());
            System.out.println("With arguments: " + request.arguments());
            
            // Execute the tool (this would normally be done by the framework)
            String result = executeToolRequest(request, calculatorTool, weatherTool);
            
            // Create tool execution result message
            ToolExecutionResultMessage resultMessage = ToolExecutionResultMessage.from(
                request.id(),
                request.name(),
                result
            );
            
            System.out.println("Tool result: " + resultMessage.text());
            System.out.println("Result will be sent back to LLM for final response generation");
        }
        
        System.out.println("\n\nFlow Summary:");
        System.out.println("=============");
        System.out.println("1. @Tool annotated methods → Tool specifications generated");
        System.out.println("2. Tool specifications sent to LLM in ChatRequest");
        System.out.println("3. LLM returns AiMessage with ToolExecutionRequest objects");
        System.out.println("4. Framework executes tools and creates ToolExecutionResultMessage");
        System.out.println("5. Results sent back to LLM for final response synthesis");
    }
    
    /**
     * Simulates what the LLM would return when it decides to use tools
     */
    private static List<ToolExecutionRequest> simulateLLMToolRequests() {
        // Simulate LLM deciding to calculate 15 + 25 and get weather for Paris
        ToolExecutionRequest addRequest = ToolExecutionRequest.builder()
            .id("tool_call_1")
            .name("add")
            .arguments("{\"a\": 15.0, \"b\": 25.0}")
            .build();
            
        ToolExecutionRequest weatherRequest = ToolExecutionRequest.builder()
            .id("tool_call_2")
            .name("getCurrentWeather")
            .arguments("{\"city\": \"Paris\", \"countryCode\": \"FR\"}")
            .build();
            
        return Arrays.asList(addRequest, weatherRequest);
    }
    
    /**
     * Simulates tool execution based on the tool request
     * In a real application, this would be handled by the LangChain4J framework
     */
    private static String executeToolRequest(ToolExecutionRequest request, 
                                           CalculatorTool calculatorTool, 
                                           WeatherTool weatherTool) {
        try {
            switch (request.name()) {
                case "add":
                    // Parse arguments and execute
                    Map<String, Object> args = parseJsonArguments(request.arguments());
                    double a = ((Number) args.get("a")).doubleValue();
                    double b = ((Number) args.get("b")).doubleValue();
                    return String.valueOf(calculatorTool.add(a, b));
                    
                case "getCurrentWeather":
                    args = parseJsonArguments(request.arguments());
                    String city = (String) args.get("city");
                    String countryCode = (String) args.get("countryCode");
                    return weatherTool.getCurrentWeather(city, countryCode);
                    
                default:
                    return "Unknown tool: " + request.name();
            }
        } catch (Exception e) {
            return "Error executing tool: " + e.getMessage();
        }
    }
    
    /**
     * Simple JSON argument parsing for demo purposes
     * In real applications, use a proper JSON library
     */
    private static Map<String, Object> parseJsonArguments(String jsonArgs) {
        // Simplified parsing for demo - in real code use Jackson or similar
        Map<String, Object> args = new java.util.HashMap<>();
        
        // Remove braces and split by comma
        String content = jsonArgs.replaceAll("[{}]", "");
        String[] pairs = content.split(",");
        
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            String key = keyValue[0].trim().replaceAll("\"", "");
            String value = keyValue[1].trim().replaceAll("\"", "");
            
            // Try to parse as number first
            try {
                if (value.contains(".")) {
                    args.put(key, Double.parseDouble(value));
                } else {
                    args.put(key, Integer.parseInt(value));
                }
            } catch (NumberFormatException e) {
                // If not a number, treat as string
                args.put(key, value);
            }
        }
        
        return args;
    }
}
