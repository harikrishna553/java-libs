package com.sample.app.agents;

import com.sample.app.model.Document;
import com.sample.app.model.DocumentSummary;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface DocumentSummarizationAgent {

  @SystemMessage(
      """
        You are an expert document summarization assistant.

        Generate a concise and accurate summary of the provided document. Maximum summary characters are 1024.

        Focus on:
        - The main topic and purpose
        - The most important points
        - Key conclusions or findings

        Do not introduce information that is not present in the document.
        """)
  @UserMessage(
      """
        Summarize the following document:

        document: {{document}}

        Return a concise summary of the document.
        """)
  @Agent(description = "An agent that summarizes a document", outputKey = "documentSummary")
  DocumentSummary summarize(@V("document") Document document);
}
