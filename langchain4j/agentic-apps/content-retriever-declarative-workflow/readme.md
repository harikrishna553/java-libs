## Build the Project

Open a terminal and navigate to the directory containing the `pom.xml` file. Then build the project using the following command:

```bash
mvn clean package
```

---

## Run the Application

Once the build completes successfully, the executable JAR file will be generated in the `target` directory.

Start the application using the following command:

```bash
java -jar ./target/content-retriever-declarative-workflow-1.0.0.jars
```

If everything is configured correctly, the application will start and you can see following messages in the console

```bash
$ java -jar ./target/content-retriever-declarative-workflow-1.0.0.jar 
==================================================
           Company Policy Knowledge Agent
==================================================

This demo uses company-policies.md as the
knowledge base for semantic policy retrieval.

This agentic workflow will:
  1. Load company-policies.md from src/main/resources.
  2. Split the policy document into smaller TextSegments.
  3. Generate embeddings using BgeSmallEnV15QuantizedEmbeddingModel.
  4. Store embeddings and TextSegments in InMemoryEmbeddingStore.
  5. Accept an employee policy question.
  6. Generate an embedding for the user question.
  7. Perform semantic similarity search.
  8. Retrieve the most relevant policy segments.
  9. Use @ContentRetrieverSupplier to provide the retrieved content.
 10. Generate a grounded answer using the ChatModel.

Knowledge retrieval is handled using:
  - @ContentRetrieverSupplier
  - BgeSmallEnV15QuantizedEmbeddingModel
  - InMemoryEmbeddingStore<TextSegment>

Available policy topics:
  1. Annual Leave Policy
  2. Remote Work Policy
  3. Learning and Certification Policy
  4. Business Travel Policy
  5. Employee Learning Leave Policy
  6. Information Security Policy
  7. Flexible Working Hours Policy
  8. Employee Referral Policy
  9. Code of Conduct

Example questions:
  - How many annual leave days do employees get?
  - I am planning a vacation. How many paid days off can I take?
  - Can I work remotely during the week?
  - What are the requirements for working from home?
  - Does the company reimburse professional certifications?
  - How much certification reimbursement can I claim?
  - When should business travel expenses be submitted?
  - Can I take leave for a certification exam?
  - Where should API keys and database passwords be stored?
  - What are the expectations for flexible working hours?
  - How does the employee referral policy work?
  - What does the company code of conduct expect from employees?

Tip:
  Try asking the question using different wording.
  The demo uses semantic similarity instead of
  exact keyword matching.

Enter a company policy question: 
```
