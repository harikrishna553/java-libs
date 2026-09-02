package com.sample.app.agents;

import dev.langchain4j.agentic.observability.AgentListener;
import dev.langchain4j.agentic.observability.AgentRequest;
import dev.langchain4j.agentic.observability.AgentResponse;
import dev.langchain4j.agentic.scope.AgenticScope;

public class MyAgentListener implements AgentListener {

    @Override
    public void beforeAgentInvocation(AgentRequest request) {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("Starting Agent : " + request.agentName());
        System.out.println("==========================================");

        System.out.println("Inputs:");
        request.inputs().forEach(
                (key, value) ->
                        System.out.println("  " + key + " = " + value)
        );
    }

    @Override
    public void afterAgentInvocation(AgentResponse response) {

        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println("Completed Agent: " + response.agentName());
        System.out.println("------------------------------------------");

        System.out.println("Output:");
        System.out.println(response.output());

        printAgenticScope(response.agenticScope());
    }

    private void printAgenticScope(AgenticScope scope) {

        System.out.println();
        System.out.println("AgenticScope after agent execution:");
        System.out.println("------------------------------------------");

        if (scope == null) {
            System.out.println("  <no AgenticScope>");
            return;
        }

        scope.state().forEach(
                (key, value) ->
                        System.out.println(
                                "  " + key + " = " + value
                        )
        );

        System.out.println("------------------------------------------");
    }

    @Override
    public boolean inheritedBySubagents() {
        return true;
    }
}