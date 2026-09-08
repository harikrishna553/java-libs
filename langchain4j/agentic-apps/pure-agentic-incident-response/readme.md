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
java -jar ./target/pure-agentic-incident-response-1.0.0.jar
```

If everything is configured correctly, the application will start, and you will be able to see following messages in the console.

```code
$ java -jar ./target/pure-agentic-incident-response-1.0.0.jar 

==========================================
USER REQUEST
==========================================

The checkout-service started returning
HTTP 500 errors immediately after
today's deployment.

Investigate the incident,
identify the most probable root cause,
and recommend the safest next action.

[TOOL] Searching logs for: checkout-service
[TOOL] Reading startup logs for: checkout-service
[TOOL] Searching logs for: checkout-service
[TOOL] Checking dependencies for: checkout-service
[TOOL] Checking deployment for: checkout-service
[TOOL] Checking configuration changes for: checkout-service
[TOOL] Searching logs for: checkout-service
[TOOL] Reading startup logs for: checkout-service
[TOOL] Searching logs for: checkout-service
[TOOL] Loading runbook for: Revert database connection pool configuration to previous version

==========================================
SUPERVISOR RESPONSE
==========================================

The probable root cause of the HTTP 500 errors is a temporary issue with the database connection, such as a network issue or a database maintenance task, affecting the HikariPool-1 connection pool. The recommended immediate action is to revert the database connection pool configuration to its previous version using the standard configuration pipeline. This will help to stabilize the service and prevent further errors. Verification steps include monitoring the service health and error rate, checking database connection logs, and verifying network connectivity. Follow-up actions involve identifying and resolving underlying network issues, scheduling regular database maintenance, implementing monitoring and alerting mechanisms, and reviewing database connection pool configuration.
```