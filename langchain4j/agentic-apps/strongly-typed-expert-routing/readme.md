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
java -jar ./target/strongly-typed-expert-routing-1.0.0.jar
```

If everything is configured correctly, the application will start, and you can begin interacting with the Agent. When prompted, enter the topic that you are interested for.

```bash
$ java -jar ./target/strongly-typed-expert-routing-1.0.0.jar 
==============================================
      Strongly Typed Expert Routing
==============================================

This LangChain4j agentic workflow demonstrates:

  1. Strongly typed agent inputs
  2. TypedKey<T>
  3. @K annotation
  4. typedOutputKey
  5. AgenticScope
  6. Conditional agent routing
  7. Strongly typed expert responses

Available experts:
  - Medical Expert
  - Technical Expert
  - Legal Expert

Example questions:
  Medical   : I injured my ankle. What should I do?
  Technical : Explain Kubernetes autoscaling.
  Legal     : What should I check before signing a contract?

Enter your question:
```

For example, you can try with the topic "I injured my ankle. What should I do?".