package com.sample.app.agents;

import com.sample.app.model.Document;
import com.sample.app.model.DocumentSummary;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.planner.AgentInstance;
import dev.langchain4j.service.V;
import java.util.List;

public interface BatchDocumentSummarizer extends AgentInstance {

  @Agent
  List<DocumentSummary> summarizeDocuments(@V("documents") List<Document> documents);
}
