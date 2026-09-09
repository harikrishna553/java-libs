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
java -jar ./target/supervisor-customization-demo-1.0.0.jar
```

If everything is configured correctly, the application will start, and you will be able to see following messages in the console.

```code
$ java -jar ./target/supervisor-customization-demo-1.0.0.jar 

==========================================
SUPERVISOR RESPONSE STRATEGY
==========================================

Select how the final response should be returned:

1. SCORED
2. SUMMARY
3. LAST
Enter your choice [1-3]: 3

Selected Response Strategy: LAST

==========================================
SUPERVISOR CONTEXT STRATEGY
==========================================

Select how Supervisor context should be generated:

1. CHAT_MEMORY
2. SUMMARIZATION
3. CHAT_MEMORY_AND_SUMMARIZATION
Enter your choice [1-3]: 3

Selected Context Strategy: CHAT_MEMORY_AND_SUMMARIZATION

==========================================
SUPERVISOR CONFIGURATION
==========================================

Response Strategy : LAST
Context Strategy  : CHAT_MEMORY_AND_SUMMARIZATION

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
[TOOL] Checking deployment for: checkout-service
[TOOL] Checking configuration changes for: checkout-service
[TOOL] Reading metrics for: checkout-service
[TOOL] Loading runbook for: Revert DB_POOL_MAX and DB_CONNECTION_TIMEOUT_MS changes

==========================================
SUPERVISOR RESPONSE
==========================================

Based on the investigation findings, I recommend the following:

**1. Probable Root Cause:**
The probable root cause of the HTTP 500 errors is a misconfiguration of the database connection pool and timeout settings, leading to a rapid increase in database connection acquisition timeouts. The high CPU usage of the application suggests that it is struggling to handle the increased load, which may be contributing to the errors.

**2. Recommended Immediate Action:**
To mitigate the issue immediately, I recommend reverting the recent changes to `DB_POOL_MAX` and `DB_CONNECTION_TIMEOUT_MS`. This will help to stabilize the database connection pool and reduce the number of connection acquisition timeouts.

**3. Verification Steps:**

a. Verify that the HTTP 500 error rate has decreased to a stable level (e.g., < 5%).
b. Check the p95 latency to ensure it has decreased to a reasonable level (e.g., < 500 ms).
c. Monitor the database connection pool to ensure it is within a healthy range (e.g., 20-40 active connections).
d. Verify that the waiting DB requests and database connection acquisition timeouts have decreased.

**4. Follow-up Action:**
After verifying that the immediate action has resolved the issue, I recommend conducting a thorough investigation to identify the root cause of the problem. This may involve:

a. Reviewing the deployment process and logs to identify any potential issues.
b. Analyzing the application and database performance to identify any bottlenecks or areas for improvement.
c. Collaborating with the development team to implement any necessary changes to the application or database configuration.

By taking a proactive and methodical approach, we can ensure that the issue is fully resolved and prevent similar incidents from occurring in the future.
```