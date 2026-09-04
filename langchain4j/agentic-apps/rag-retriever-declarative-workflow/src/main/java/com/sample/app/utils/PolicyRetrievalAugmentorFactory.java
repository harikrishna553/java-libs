package com.sample.app.utils;

import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.injector.ContentInjector;
import dev.langchain4j.rag.content.injector.DefaultContentInjector;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;

import java.util.List;

public final class PolicyRetrievalAugmentorFactory {

	private PolicyRetrievalAugmentorFactory() {
	}

	public static RetrievalAugmentor create() {

		System.out.println("@RetrievalAugmentorSupplier -> " + "Creating RetrievalAugmentor");

		/*
		 * Step 1: Create the ContentRetriever.
		 */
		ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
				.embeddingStore(PolicyKnowledgeBase.embeddingStore())
				.embeddingModel(PolicyKnowledgeBase.embeddingModel()).maxResults(3).minScore(0.5).build();

		/*
		 * Step 2:
		 * Define how the retrieved information should be injected into the
		 * final UserMessage that will be sent to the ChatModel.
		 *
		 * DefaultContentInjector supports the following predefined template variables:
		 *
		 * 1. {{userMessage}}
		 *    - Provided automatically by LangChain4j's DefaultContentInjector.
		 *    - Represents the original UserMessage received from the agent
		 *      before retrieved RAG content is injected.
		 *
		 *    Example:
		 *
		 *        agent.ask("Elaborate Annual Leave Policy")
		 *
		 *    If the agent contains:
		 *
		 *        @UserMessage("""
		 *            Answer the following employee policy question:
		 *            {{question}}
		 *            """)
		 *
		 *    then {{userMessage}} will contain something similar to:
		 *
		 *        Answer the following employee policy question:
		 *        Elaborate Annual Leave Policy
		 *
		 *
		 * 2. {{contents}}
		 *    - Provided automatically by LangChain4j's DefaultContentInjector.
		 *    - Contains the relevant Content objects returned by the
		 *      RetrievalAugmentor's retrieval pipeline.
		 *
		 *    In this example, the ContentRetriever performs semantic search
		 *    against the company-policy EmbeddingStore.
		 *
		 *    For a question such as:
		 *
		 *        "Elaborate Annual Leave Policy"
		 *
		 *    {{contents}} may contain:
		 *
		 *        Annual Leave Policy
		 *
		 *        Every full-time employee receives 20 days of annual leave
		 *        per calendar year.
		 *
		 *        Employees can carry forward a maximum of 10 unused
		 *        annual leave days...
		 *
		 *
		 * These two variables are NOT supplied through @V.
		 *
		 * They are predefined variables understood and populated internally
		 * by DefaultContentInjector.
		 *
		 * Runtime flow:
		 *
		 *      Original UserMessage
		 *              |
		 *              v
		 *      RetrievalAugmentor
		 *              |
		 *              v
		 *      ContentRetriever
		 *              |
		 *              v
		 *      Retrieved List<Content>
		 *              |
		 *              v
		 *      DefaultContentInjector
		 *          |           |
		 *          |           |
		 *          v           v
		 *   {{userMessage}}  {{contents}}
		 *          \           /
		 *           \         /
		 *            v       v
		 *        PromptTemplate
		 *              |
		 *              v
		 *      Augmented UserMessage
		 *              |
		 *              v
		 *          ChatModel
		 *
		 * metadataKeysToInclude(List.of("source"))
		 * tells DefaultContentInjector to also include the "source" metadata
		 * associated with each retrieved TextSegment when formatting
		 * {{contents}}.
		 */
		ContentInjector contentInjector =
		        DefaultContentInjector.builder()
		                .promptTemplate(
		                        PromptTemplate.from("""
		                                
		                                Employee Question:
		                                {{userMessage}}
		                                
		                                ----------------------------------------
		                                COMPANY POLICY CONTEXT
		                                ----------------------------------------
		                                
		                                {{contents}}
		                                
		                                ----------------------------------------
		                                
		                                Instructions:
		                                
		                                Answer the employee's question using
		                                only the company policy context above.
		                                
		                                If the answer cannot be determined
		                                from the supplied policy context,
		                                clearly say:
		                                
		                                "The available company policy does not
		                                contain enough information to answer
		                                this question."
		                                
		                                Do not invent company rules or policies.
		                                """)
		                )
		                .metadataKeysToInclude(
		                        List.of("source")
		                )
		                .build();

		/*
		 * Step 3: Assemble the advanced RAG pipeline.
		 */
		return DefaultRetrievalAugmentor.builder().contentRetriever(contentRetriever).contentInjector(contentInjector)
				.build();
	}
}