package com.sample.app;

import com.sample.app.agents.CompanyPolicyAgent;
import dev.langchain4j.agentic.AgenticServices;
import java.util.Scanner;

public class App {

  private static void printIntroduction() {

    System.out.println("==================================================");
    System.out.println("           Company Policy Knowledge Agent");
    System.out.println("==================================================");
    System.out.println();

    System.out.println("This demo uses company-policies.md as the");
    System.out.println("knowledge base for semantic policy retrieval.");
    System.out.println();

    System.out.println("This agentic workflow will:");
    System.out.println("  1. Load company-policies.md from src/main/resources.");
    System.out.println("  2. Split the policy document into smaller TextSegments.");
    System.out.println("  3. Generate embeddings using BgeSmallEnV15QuantizedEmbeddingModel.");
    System.out.println("  4. Store embeddings and TextSegments in InMemoryEmbeddingStore.");
    System.out.println("  5. Accept an employee policy question.");
    System.out.println("  6. Generate an embedding for the user question.");
    System.out.println("  7. Perform semantic similarity search.");
    System.out.println("  8. Retrieve the most relevant policy segments.");
    System.out.println("  9. Use @ContentRetrieverSupplier to provide the retrieved content.");
    System.out.println(" 10. Generate a grounded answer using the ChatModel.");
    System.out.println();

    System.out.println("Knowledge retrieval is handled using:");
    System.out.println("  - @ContentRetrieverSupplier");
    System.out.println("  - BgeSmallEnV15QuantizedEmbeddingModel");
    System.out.println("  - InMemoryEmbeddingStore<TextSegment>");
    System.out.println();

    System.out.println("Available policy topics:");
    System.out.println("  1. Annual Leave Policy");
    System.out.println("  2. Remote Work Policy");
    System.out.println("  3. Learning and Certification Policy");
    System.out.println("  4. Business Travel Policy");
    System.out.println("  5. Employee Learning Leave Policy");
    System.out.println("  6. Information Security Policy");
    System.out.println("  7. Flexible Working Hours Policy");
    System.out.println("  8. Employee Referral Policy");
    System.out.println("  9. Code of Conduct");
    System.out.println();

    System.out.println("Example questions:");
    System.out.println("  - How many annual leave days do employees get?");
    System.out.println("  - I am planning a vacation. How many paid days off can I take?");
    System.out.println("  - Can I work remotely during the week?");
    System.out.println("  - What are the requirements for working from home?");
    System.out.println("  - Does the company reimburse professional certifications?");
    System.out.println("  - How much certification reimbursement can I claim?");
    System.out.println("  - When should business travel expenses be submitted?");
    System.out.println("  - Can I take leave for a certification exam?");
    System.out.println("  - Where should API keys and database passwords be stored?");
    System.out.println("  - What are the expectations for flexible working hours?");
    System.out.println("  - How does the employee referral policy work?");
    System.out.println("  - What does the company code of conduct expect from employees?");
    System.out.println();

    System.out.println("Tip:");
    System.out.println("  Try asking the question using different wording.");
    System.out.println("  The demo uses semantic similarity instead of");
    System.out.println("  exact keyword matching.");
    System.out.println();

    System.out.print("Enter a company policy question: ");
  }

  public static void main(String[] args) {
    printIntroduction();
    String topic = null;

    try (Scanner scanner = new Scanner(System.in)) {
      topic = scanner.nextLine().trim();
    }

    CompanyPolicyAgent companyPolicyAgent =
        AgenticServices.createAgenticSystem(CompanyPolicyAgent.class);
    String response = companyPolicyAgent.ask(topic);

    System.out.println(response);
  }
}
