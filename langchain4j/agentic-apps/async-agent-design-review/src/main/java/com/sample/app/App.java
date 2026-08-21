package com.sample.app;

import com.sample.app.agents.AgentWorkflowListener;
import com.sample.app.agents.ArchitectureAssessmentAgent;
import com.sample.app.agents.CostReviewAgent;
import com.sample.app.agents.FinalDesignReviewAgent;
import com.sample.app.agents.ScalabilityReviewAgent;
import com.sample.app.agents.SecurityReviewAgent;
import com.sample.app.config.OllamaConfig;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.scope.ResultWithAgenticScope;
import dev.langchain4j.model.chat.ChatModel;
import java.util.HashMap;
import java.util.Map;

public class App {

  private static String designDoc =
      """
			Software Design: E-Commerce Order Management Platform
			------------------------------------------------------

			1. Overview
			The platform is a Spring Boot based e-commerce order management system.
			Customers can browse products, place orders, make payments, and track
			their orders.

			The system is expected to support approximately 10 million registered
			customers and up to 100,000 concurrent users during peak events such as
			major sales.

			2. Architecture
			The application is deployed as multiple Spring Boot microservices on
			Kubernetes.

			The main services are:

			- API Gateway
			- User Service
			- Product Service
			- Order Service
			- Payment Service
			- Notification Service

			All services communicate synchronously using REST APIs for most
			operations.

			Kafka is used for asynchronous events such as:
			- OrderCreated
			- PaymentCompleted
			- OrderShipped
			- OrderCancelled

			3. Database
			Each microservice has its own PostgreSQL database.

			The Order Service stores order and order-item information.
			The Payment Service stores payment transaction information.
			The Product Service stores product and inventory information.

			PostgreSQL databases use read replicas for read-heavy workloads.

			4. Caching
			Redis is used as a distributed cache.

			Product information, product availability, and frequently accessed
			customer information are cached in Redis.

			Cache entries have a TTL of 10 minutes.

			5. Infrastructure
			The application runs on Kubernetes.

			Each service has between 3 and 10 pods depending on traffic.

			Kubernetes Horizontal Pod Autoscaler is configured based on CPU
			utilization.

			The API Gateway is exposed to the internet through a cloud load balancer.

			6. Authentication and Authorization
			Customers authenticate using OAuth 2.0 and receive JWT tokens.

			The API Gateway validates the JWT token before forwarding requests
			to downstream services.

			Internal service-to-service communication uses HTTPS.

			Service credentials and database passwords are stored as Kubernetes
			Secrets.

			7. Order Processing
			When a customer places an order:

			1. The API Gateway forwards the request to the Order Service.
			2. The Order Service validates the request.
			3. The Order Service calls the Product Service to verify inventory.
			4. The Order Service creates the order in PostgreSQL.
			5. An OrderCreated event is published to Kafka.
			6. The Payment Service consumes the event and processes the payment.
			7. After successful payment, a PaymentCompleted event is published.
			8. The Notification Service consumes the event and sends an email
			   to the customer.

			8. Observability
			Application logs are collected centrally.

			Prometheus is used for metrics and Grafana is used for dashboards.

			Distributed tracing is not currently enabled.

			Alerts are configured for high CPU utilization, high error rates,
			and unavailable Kubernetes pods.

			9. Deployment
			The application is deployed using a CI/CD pipeline.

			Each microservice is deployed independently.

			Kubernetes rolling deployments are used for production releases.

			10. Known Constraints
			The platform should maintain 99.9% availability.

			Order placement should normally complete within 2 seconds.

			The system should be able to scale horizontally during major
			promotional events without manual intervention.

			The engineering team wants to minimize infrastructure cost while
			maintaining the required availability and performance.
			""";

  public static void main(String[] args) {
    ChatModel chatModel = OllamaConfig.createChatModel();

    SecurityReviewAgent securityReviewAgent =
        AgenticServices.agentBuilder(SecurityReviewAgent.class)
            .chatModel(chatModel)
            .async(true)
            .build();

    ScalabilityReviewAgent scalabilityReviewAgent =
        AgenticServices.agentBuilder(ScalabilityReviewAgent.class)
            .chatModel(chatModel)
            .async(true)
            .build();

    CostReviewAgent costReviewAgent =
        AgenticServices.agentBuilder(CostReviewAgent.class)
            .chatModel(chatModel)
            .async(true)
            .build();

    ArchitectureAssessmentAgent architectureAssessmentAgent =
        AgenticServices.agentBuilder(ArchitectureAssessmentAgent.class)
            .chatModel(chatModel)
            .build();

    FinalDesignReviewAgent finalDesignReviewAgent =
        AgenticServices.agentBuilder(FinalDesignReviewAgent.class).chatModel(chatModel).build();

    UntypedAgent storyWorkflow =
        AgenticServices.sequenceBuilder()
            .subAgents(
                securityReviewAgent,
                scalabilityReviewAgent,
                costReviewAgent,
                architectureAssessmentAgent,
                finalDesignReviewAgent)
            .listener(new AgentWorkflowListener())
            .build();

    Map<String, Object> input = new HashMap<>();
    input.put("design", designDoc);

    ResultWithAgenticScope<String> agenticScope = storyWorkflow.invokeWithAgenticScope(input);

    Object finalReport = agenticScope.agenticScope().readState("finalReport");

    System.out.println("\n\nFinal Report:");
    System.out.println(
        "----------------------------------------------------------------------------");
    System.out.println(finalReport);
    System.out.println(
        "----------------------------------------------------------------------------");
    System.exit(0);
  }
}
