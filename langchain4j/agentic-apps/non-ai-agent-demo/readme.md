Build the application using Maven:

```bash
mvn clean package
```
Run the generated executable JAR:

```java
java -jar ./target/non-ai-agent-demo-1.0.0.jar
```
You can able to see following kind of messages in the console.

```java
$ java -jar ./target/non-ai-agent-demo-1.0.0.jar 

============================================
LANGCHAIN4J HYBRID AGENTIC SYSTEM
AI Agents + Non-AI Agents
============================================


USER INCIDENT
=============
Customers are reporting intermittent HTTP 500
errors while placing orders through the
checkout service.

The issue started shortly after today's
application deployment.

Users can browse products normally, but some
checkout requests are failing.


>>> agentAction
Normalizing serviceName: checkout-service -> checkout-service

>>> NON-AI AGENT: ServiceHealthAgent

[MonitoringService] Calling monitoring API for: checkout-service

>>> NON-AI AGENT: DeploymentLookupAgent

[DeploymentService] Looking up latest deployment for: checkout-service

>>> NON-AI AGENT: PriorityCalculatorAgent

>>> NON-AI AGENT: TicketCreationAgent

[TicketService] Creating ticket INC-1001

============================================
FINAL INCIDENT TICKET
============================================
Ticket ID      : INC-1001
Service        : checkout-service
Priority       : P2
Status         : DEGRADED
Error Rate     : 18.7%
Latency        : 1250 ms
Version        : 4.7.2
Previous       : 4.7.1

ROOT CAUSE ANALYSIS
--------------------------------------------
**Root-Cause Analysis**

Based on the provided evidence, the following analysis can be conducted:

* **Hypothesis**: The intermittent HTTP 500 errors in the checkout service are related to the latest deployment.
* **Evidence**: The issue started shortly after the deployment, and users can browse products normally, but some checkout requests are failing.
* **Root Cause**: The intermittent errors are likely due to a configuration or code change introduced in the latest deployment that is causing the service to fail under certain conditions.

**Reasoning**:

1. The issue started after the deployment, suggesting a connection between the deployment and the problem.
2. The fact that users can browse products normally indicates that the service is not entirely down, but rather experiencing intermittent failures.
3. The HTTP 500 error code suggests a server-side issue, which could be related to the deployment.

**Next Investigation Step**:

* Review the deployment logs to identify any changes or configurations that may be causing the issue.
* Check the service's error logs to see if there are any error messages or stack traces that can provide more insight into the problem.

============================================
```