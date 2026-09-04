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
java -jar ./target/rag-retriever-declarative-workflow-1.0.0.jar 
```

If everything is configured correctly, the application will start, and you can begin interacting with the Agent. When prompted, enter the topic that you are interested for.

```bash
$ java -jar ./target/rag-retriever-declarative-workflow-1.0.0.jar 
==========================================
   @RetrievalAugmentorSupplier Demo
==========================================

This agentic workflow will:
  1. Accept an employee policy question.
  2. Use @RetrievalAugmentorSupplier.
  3. Supply a RetrievalAugmentor.
  4. Perform semantic retrieval.
  5. Retrieve relevant company policy content.
  6. Inject retrieved content using a custom prompt.
  7. Send the augmented message to the ChatModel.
  8. Generate a grounded policy answer.

Available policy topics:
  - Annual Leave Policy
  - Remote Work / Work From Home Policy
  - Learning & Certification Policy
  - Sick Leave Policy

Example questions:
  - How many annual leave days do I get?
  - How many leave days can I carry forward?
  - How many days can I work from home?
  - Does the company reimburse certifications?
  - How many learning hours do employees get?

@ChatModelSupplier -> Providing ChatModel
@RetrievalAugmentorSupplier invoked
@RetrievalAugmentorSupplier -> Creating RetrievalAugmentor

Initializing Company Policy Knowledge Base...
Company Policy Knowledge Base initialized.

@ChatModelSupplier -> Providing ChatModel

------------------------------------------
Ask a company policy question (or type 'exit'):
```

For example, you can try with the topic "Summarize Leave Policies".