package com.sample.app.tools;

import dev.langchain4j.agent.tool.Tool;

public class MathTool {

    @Tool("Adds two integers")
    public int add(int a, int b) {
        return a + b;
    }

}
