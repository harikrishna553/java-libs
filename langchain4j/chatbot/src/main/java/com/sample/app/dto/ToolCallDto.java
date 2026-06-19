package com.sample.app.dto;

import java.util.Map;

public class ToolCallDto {

    private String name;
    private String description;
    private Map<String, Object> input;
    private Object output;

    public ToolCallDto() {}

    public ToolCallDto(String name, String description, Map<String, Object> input, Object output) {
        this.name = name;
        this.description = description;
        this.input = input;
        this.output = output;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getInput() {
        return input;
    }

    public void setInput(Map<String, Object> input) {
        this.input = input;
    }

    public Object getOutput() {
        return output;
    }

    public void setOutput(Object output) {
        this.output = output;
    }
}
