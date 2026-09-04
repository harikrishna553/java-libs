package com.sample.app;

import java.util.Scanner;

import com.sample.app.agents.CompanyPolicyAgent;

import dev.langchain4j.agentic.AgenticServices;

public class App {

	public static void main(String[] args) {

		printIntroduction();

		CompanyPolicyAgent agent = AgenticServices.createAgenticSystem(CompanyPolicyAgent.class);

		Scanner scanner = new Scanner(System.in);

		while (true) {

			System.out.println();
			System.out.println("------------------------------------------");
			System.out.print("Ask a company policy question " + "(or type 'exit'): ");

			String question = scanner.nextLine().trim();

			if ("exit".equalsIgnoreCase(question)) {
				System.out.println();
				System.out.println("Exiting application.");
				break;
			}

			if (question.isBlank()) {
				continue;
			}

			System.out.println();
			System.out.println("Processing question...");
			System.out.println();

			String answer = agent.ask(question);

			System.out.println("------------------------------------------");
			System.out.println("ANSWER");
			System.out.println("------------------------------------------");
			System.out.println(answer);
		}
	}

	private static void printIntroduction() {

		System.out.println("==========================================");
		System.out.println("   @RetrievalAugmentorSupplier Demo");
		System.out.println("==========================================");

		System.out.println();

		System.out.println("This agentic workflow will:");

		System.out.println("  1. Accept an employee policy question.");

		System.out.println("  2. Use @RetrievalAugmentorSupplier.");

		System.out.println("  3. Supply a RetrievalAugmentor.");

		System.out.println("  4. Perform semantic retrieval.");

		System.out.println("  5. Retrieve relevant company policy content.");

		System.out.println("  6. Inject retrieved content using a custom prompt.");

		System.out.println("  7. Send the augmented message to the ChatModel.");

		System.out.println("  8. Generate a grounded policy answer.");

		System.out.println();

		System.out.println("Available policy topics:");

		System.out.println("  - Annual Leave Policy");

		System.out.println("  - Remote Work / Work From Home Policy");

		System.out.println("  - Learning & Certification Policy");

		System.out.println("  - Sick Leave Policy");

		System.out.println();

		System.out.println("Example questions:");

		System.out.println("  - How many annual leave days do I get?");

		System.out.println("  - How many leave days can I carry forward?");

		System.out.println("  - How many days can I work from home?");

		System.out.println("  - Does the company reimburse certifications?");

		System.out.println("  - How many learning hours do employees get?");

		System.out.println();
	}
}