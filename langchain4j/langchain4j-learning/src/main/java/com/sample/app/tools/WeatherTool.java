package com.sample.app.tools;

import dev.langchain4j.agent.tool.Tool;

/**
 * Example tool class demonstrating weather-related operations with the @Tool annotation.
 * 
 * This class shows how LangChain4J handles different parameter types including
 * strings, enums, and optional parameters in tool specifications.
 */
public class WeatherTool {
    
    @Tool("Get current weather information for a specified city")
    public String getCurrentWeather(
        @dev.langchain4j.agent.tool.P("The name of the city to get weather for") String city,
        @dev.langchain4j.agent.tool.P("The country code (optional, e.g., 'US', 'UK')") String countryCode
    ) {
        String location = countryCode != null ? city + ", " + countryCode : city;
        String weather = "Sunny, 22°C (72°F)"; // Simulated weather data
        System.out.println("Executing getCurrentWeather(" + location + ") = " + weather);
        return "Current weather in " + location + ": " + weather;
    }
    
    @Tool("Get weather forecast for the next few days")
    public String getWeatherForecast(
        @dev.langchain4j.agent.tool.P("The name of the city to get forecast for") String city,
        @dev.langchain4j.agent.tool.P("Number of days to forecast (1-7)") int days
    ) {
        if (days < 1 || days > 7) {
            throw new IllegalArgumentException("Forecast days must be between 1 and 7");
        }
        
        StringBuilder forecast = new StringBuilder();
        forecast.append("Weather forecast for ").append(city).append(" (").append(days).append(" days):\n");
        
        for (int i = 1; i <= days; i++) {
            int temp = 20 + (i % 3) * 5; // Simulated varying temperatures
            forecast.append("Day ").append(i).append(": Partly cloudy, ").append(temp).append("°C\n");
        }
        
        String result = forecast.toString();
        System.out.println("Executing getWeatherForecast(" + city + ", " + days + " days)");
        return result;
    }
    
    @Tool("Convert temperature between Celsius and Fahrenheit")
    public String convertTemperature(
        @dev.langchain4j.agent.tool.P("The temperature value to convert") double temperature,
        @dev.langchain4j.agent.tool.P("The source unit: 'C' for Celsius or 'F' for Fahrenheit") String fromUnit,
        @dev.langchain4j.agent.tool.P("The target unit: 'C' for Celsius or 'F' for Fahrenheit") String toUnit
    ) {
        if (!isValidTemperatureUnit(fromUnit) || !isValidTemperatureUnit(toUnit)) {
            throw new IllegalArgumentException("Temperature units must be 'C' or 'F'");
        }
        
        if (fromUnit.equalsIgnoreCase(toUnit)) {
            return temperature + "°" + toUnit + " = " + temperature + "°" + toUnit;
        }
        
        double convertedTemp;
        if (fromUnit.equalsIgnoreCase("C") && toUnit.equalsIgnoreCase("F")) {
            convertedTemp = (temperature * 9.0 / 5.0) + 32;
        } else {
            convertedTemp = (temperature - 32) * 5.0 / 9.0;
        }
        
        String result = String.format("%.1f°%s = %.1f°%s", temperature, fromUnit, convertedTemp, toUnit);
        System.out.println("Executing convertTemperature(" + temperature + "°" + fromUnit + " to " + toUnit + ") = " + convertedTemp + "°" + toUnit);
        return result;
    }
    
    @Tool("Check if weather conditions are suitable for outdoor activities")
    public String checkOutdoorConditions(
        @dev.langchain4j.agent.tool.P("The name of the city to check") String city,
        @dev.langchain4j.agent.tool.P("The type of activity (e.g., 'hiking', 'picnic', 'sports')") String activity
    ) {
        // Simulated weather analysis
        boolean isSuitable = Math.random() > 0.3; // 70% chance of good weather
        String conditions = isSuitable ? "excellent" : "poor";
        String reason = isSuitable ? 
            "clear skies and mild temperatures" : 
            "rain expected and strong winds";
            
        String result = String.format("Weather conditions in %s are %s for %s: %s", 
                                    city, conditions, activity, reason);
        
        System.out.println("Executing checkOutdoorConditions(" + city + ", " + activity + ")");
        return result;
    }
    
    private boolean isValidTemperatureUnit(String unit) {
        return unit != null && (unit.equalsIgnoreCase("C") || unit.equalsIgnoreCase("F"));
    }
}
