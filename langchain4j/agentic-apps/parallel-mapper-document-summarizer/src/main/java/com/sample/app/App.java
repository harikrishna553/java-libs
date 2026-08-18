package com.sample.app;

import com.sample.app.agents.BatchDocumentSummarizer;
import com.sample.app.agents.DocumentSummarizationAgent;
import com.sample.app.config.OllamaConfig;
import com.sample.app.model.Document;
import com.sample.app.model.DocumentSummary;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.model.chat.ChatModel;
import java.util.List;
import java.util.concurrent.Executors;

public class App {
  private static final List<Document> documents =
      List.of(
          new Document(
              "Introduction to AI Agents",
              """
					AI agents are software systems designed to autonomously work
					toward a specific goal. An AI agent can perceive information
					from its environment, understand a user's request, reason about
					the problem, create a plan, and take actions to accomplish the
					desired outcome.

					Unlike traditional software, where the developer explicitly
					defines every step of a workflow, an AI agent can dynamically
					determine what steps are required based on the goal, available
					information, intermediate results, and the current state of the
					environment.

					A typical AI agent follows an iterative execution cycle:
					perceive, reason, plan, act, and observe. The agent first
					understands the task and gathers relevant information. It then
					reasons about the problem and determines what should be done.
					The agent creates or selects an appropriate plan and performs
					actions using available tools. After each action, the agent
					observes the result and decides whether additional actions are
					required.

					AI agents can use tools such as APIs, databases, web search,
					calculators, file systems, enterprise applications, and other
					software services. The agent can decide which tool to use,
					when to use it, and how to use the result to continue working
					toward the goal.

					For example, a travel planning agent may receive a request to
					plan a business trip. Instead of following a fixed sequence,
					the agent can determine that it needs to understand the travel
					dates, search for flights, compare available options, find
					suitable hotels, check additional travel information, and
					finally prepare an itinerary.

					Important characteristics of AI agents include perception,
					reasoning, planning, tool usage, action execution, observation,
					adaptation, and goal-oriented behavior.

					In simple terms, traditional software is generally given a
					predefined sequence of instructions, while an AI agent is
					given a goal and can dynamically determine how to achieve that
					goal.
					"""),
          new Document(
              "Retrieval Augmented Generation",
              """
					Retrieval Augmented Generation, commonly known as RAG, is an
					approach that combines information retrieval with large
					language models. Instead of relying only on the knowledge
					contained within the language model, RAG retrieves relevant
					information from an external knowledge source and provides
					that information to the model as context before generating
					a response.

					A typical RAG pipeline consists of several stages. Documents
					are first collected from sources such as files, databases,
					knowledge bases, websites, or enterprise systems. The documents
					are then divided into smaller pieces called chunks. Each chunk
					can be converted into a numerical representation called an
					embedding and stored in a vector database or embedding store.

					When a user asks a question, the question is also converted
					into an embedding. The system searches the knowledge store
					for documents or chunks that are semantically similar to the
					question. The most relevant pieces of information are retrieved
					and added to the prompt provided to the language model.

					The language model then uses the retrieved information as
					context to generate the final response.

					A simplified RAG flow looks like this:

					User Question
					      |
					      v
					Create Query Embedding
					      |
					      v
					Search Knowledge Store
					      |
					      v
					Retrieve Relevant Documents
					      |
					      v
					Add Retrieved Context
					      |
					      v
					Large Language Model
					      |
					      v
					Generated Answer

					RAG is particularly useful when the required information is
					domain-specific, frequently changing, private, or too large
					to include directly in the model's training data.

					For example, a company can build a RAG system over internal
					technical documentation. When an employee asks a question,
					the system can retrieve the relevant sections of the
					documentation and provide them to the language model so that
					the answer is grounded in the company's knowledge.

					RAG can also help reduce hallucinations because the model has
					access to relevant source information while generating the
					response. However, the quality of the final answer depends
					heavily on the quality of document processing, chunking,
					embeddings, retrieval, and the context provided to the model.

					RAG is therefore not simply a database lookup mechanism.
					It is a pattern for combining external knowledge retrieval
					with the language generation capabilities of an LLM.
					"""),
          new Document(
              "Agentic Workflows",
              """
					   Agentic workflows are structured processes that use AI agents,
					   traditional software steps, or a combination of both to
					   accomplish complex tasks. Instead of treating an AI agent as
					   an isolated component, an agentic workflow defines how multiple
					   steps, agents, tools, decisions, and results are coordinated.

					   Agentic workflows are useful when a task contains multiple
					   activities that need to be performed in a particular order,
					   independently, conditionally, or repeatedly.

					   Different workflow patterns can be selected depending on the
					   nature of the problem.

					   Sequential workflows execute steps one after another. The
					   output of one step becomes the input for the next step. This
					   pattern is useful when each step depends on the result of the
					   previous step.

					   For example:

					   Step 1: Extract information
					          |
					          v
					   Step 2: Analyze information
					          |
					          v
					   Step 3: Generate recommendations
					          |
					          v
					   Step 4: Create final report

					   Parallel workflows execute independent tasks at the same time.
					   This can reduce overall execution time when the tasks do not
					   depend on each other.

					   For example, a software code review workflow could send the
					   same source code to multiple specialized agents in parallel:

					   Code
					     |
					     +--> Code Quality Agent
					     |
					     +--> Security Agent
					     |
					     +--> Performance Agent
					     |
					     +--> Maintainability Agent
					     |
					     v
					   Combine Findings

					   Conditional workflows introduce decision points. The next
					   step depends on the result of a previous step.

					   For example:

					   Analyze Document
					          |
					          v
					   Is the document valid?
					       /       \
					     Yes        No
					     |           |
					     v           v
					Process      Report Error

					   Parallel mapper workflows apply the same AI agent or
					   processing logic independently to a collection of inputs.
					   Each input is processed separately and the individual results
					   are collected at the end.

					   For example:

					   Collection of Inputs
					            |
					            v
					       Same AI Agent
					            |
					     +------+------+------+
					     |      |      |      |
					     v      v      v      v
					 Process  Process Process Process
					 Input 1  Input 2 Input 3 Input 4
					     |      |      |      |
					     +------+------+------+
					            |
					            v
					     Collection of Results

					   Parallel mapper workflows are useful for tasks such as
					   summarizing multiple documents, classifying multiple records,
					   extracting information from multiple files, reviewing multiple
					   code files, or analyzing multiple independent inputs.

					   The key advantage of agentic workflows is that they allow
					   complex AI applications to be decomposed into smaller,
					   manageable steps. Each step can have a specific responsibility,
					   and the workflow can coordinate these steps to produce the
					   desired result.

					   The choice of workflow pattern depends on the dependencies
					   between tasks, the need for parallel execution, decision
					   points, error handling, and the overall goal of the application.
					   """));

  public static void main(String[] args) {

    ChatModel chatModel = OllamaConfig.createChatModel();

    DocumentSummarizationAgent documentSummarizationAgent =
        AgenticServices.agentBuilder(DocumentSummarizationAgent.class).chatModel(chatModel).build();

    BatchDocumentSummarizer agent =
        AgenticServices.parallelMapperBuilder(BatchDocumentSummarizer.class)
            .subAgents(documentSummarizationAgent)
            .itemsProvider("documents")
            .executor(Executors.newFixedThreadPool(3))
            .build();

    List<DocumentSummary> documentsSummary = agent.summarizeDocuments(documents);

    for (DocumentSummary documentSumary : documentsSummary) {
      System.out.println(documentSumary);
    }

    System.exit(0);
  }
}
