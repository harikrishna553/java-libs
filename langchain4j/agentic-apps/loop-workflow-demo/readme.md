sequential-handoff-pattern-typed-agents-1.0.0.jar## Setup and Run

Build the application using Maven:

```bash
mvn clean package
```
Run the generated executable JAR:

```java
java -jar ./target/loop-workflow-demo-1.0.0.jar
```

You should see the application banner:

```bash
$java -jar ./target/loop-workflow-demo-1.0.0.jar 

  ╔════════════════════════════════════════════════════╗
  ║           ✨   Story Teller Workflow   ✨            ║
  ╚════════════════════════════════════════════════════╝

Enter Your Story Idea
```
Enter a story idea when prompted. 
For example: Tell me story about a sage lived in India