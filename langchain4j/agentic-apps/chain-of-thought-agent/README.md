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
java -jar ./target/chain-of-thought-agent-1.0.0.jar
```

If everything is configured correctly, the application will start, and you can begin interacting with the **Chain of Thought Agent** by entering logic puzzles through the console.
