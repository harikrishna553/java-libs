package com.sample.app;

import java.util.Scanner;

import com.sample.app.agents.ContentWorkflow;
import com.sample.app.config.OllamaModels;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.model.chat.ChatModel;

public class App {
	private static void printIntroduction() {

		System.out.println("==========================================");
		System.out.println("       AI Research & Content Agent");
		System.out.println("==========================================");
		System.out.println();

		System.out.println("This agentic workflow will:");
		System.out.println("  1. Analyze your topic and determine the required quality.");
		System.out.println("  2. Research the topic and gather key information.");
		System.out.println("  3. Select an appropriate ChatModel dynamically.");
		System.out.println("  4. Write the final content based on the research.");
		System.out.println();

		System.out.println("Model selection is handled using");
		System.out.println("@ChatModelSupplier and AgenticScope.");
		System.out.println();

		System.out.println("Example topics:");
		System.out.println("  - Give me a short introduction to Generative AI");
		System.out.println("  - Explain how RAG works in AI applications");
		System.out.println("  - Explain microservices architecture in detail");
		System.out.println("  - Compare REST and GraphQL with practical examples");
		System.out.println();

		System.out.print("Enter a topic to research: ");
	}

	public static void main(String[] args) {
		printIntroduction();
		ChatModel chatModel = OllamaModels.fastModel();

		String topic = null;

		try (Scanner scanner = new Scanner(System.in)) {
			topic = scanner.nextLine().trim();
		}

		ContentWorkflow contentWorkflow = AgenticServices.createAgenticSystem(ContentWorkflow.class, chatModel);
		contentWorkflow.createContent(topic);

	}

}
