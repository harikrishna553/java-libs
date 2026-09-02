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
java -jar ./target/chatmodel-supplier-demo-1.0.0.jar
```

If everything is configured correctly, the application will start, and you can begin interacting with the Agent. When prompted, enter the topic that you are interested for.

```bash
$ java -jar ./target/chatmodel-supplier-demo-1.0.0.jar 
==========================================
       AI Research & Content Agent
==========================================

This agentic workflow will:
  1. Analyze your topic and determine the required quality.
  2. Research the topic and gather key information.
  3. Select an appropriate ChatModel dynamically.
  4. Write the final content based on the research.

Model selection is handled using
@ChatModelSupplier and AgenticScope.

Example topics:
  - Give me a short introduction to Generative AI
  - Explain how RAG works in AI applications
  - Explain microservices architecture in detail
  - Compare REST and GraphQL with practical examples

Enter a topic to research: 
```

For example, you can try with the topic "In-Depth Analysis of the Indian Stock Market".