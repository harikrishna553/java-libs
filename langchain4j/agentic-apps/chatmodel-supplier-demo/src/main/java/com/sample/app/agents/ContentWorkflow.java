package com.sample.app.agents;

import dev.langchain4j.agentic.declarative.AgentListenerSupplier;
import dev.langchain4j.agentic.declarative.SequenceAgent;
import dev.langchain4j.agentic.observability.AgentListener;
import dev.langchain4j.service.V;

public interface ContentWorkflow {

    @SequenceAgent(
            outputKey = "article",
            subAgents = {
            		IntentAnalyzerAgent.class,
                    ResearchAgent.class,
                    WriterAgent.class
            }
    )
    String createContent(
            @V("topic") String topic
    );
    
    @AgentListenerSupplier
    static AgentListener agentListener() {
    	return new MyAgentListener();
    }
}