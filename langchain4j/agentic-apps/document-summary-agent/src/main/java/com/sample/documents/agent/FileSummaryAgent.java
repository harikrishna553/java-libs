package com.sample.documents.agent;

import com.sample.documents.model.DocumentSummary;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * AI Agent responsible for summarizing documents.
 *
 * <p>This agent represents the <b>Foundation Model</b> in our architecture. It does not understand
 * files, PDFs, Word documents, or metadata. Instead, it receives a clean, normalized prompt
 * produced by the Perception Module and generates an intelligent summary.
 */
public interface FileSummaryAgent {

  @SystemMessage(
      """
        You are an expert document analysis assistant.

        Populate the DocumentSummary object using only the supplied document.

        Do not invent information.
        """)
  DocumentSummary chat(@UserMessage String normalizedPrompt);
}
