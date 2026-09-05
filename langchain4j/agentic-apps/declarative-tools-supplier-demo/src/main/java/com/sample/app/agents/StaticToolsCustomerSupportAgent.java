package com.sample.app.agents;

import com.sample.app.tools.OrderTools;
import com.sample.app.tools.RefundTools;
import com.sample.app.utils.OllamaModels;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.agentic.declarative.ToolsSupplier;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StaticToolsCustomerSupportAgent {

	@SystemMessage("""
			You are a customer support agent.

			You help customers with:
			- order status
			- shipping information
			- refund eligibility

			IMPORTANT RULES:

			1. When the customer asks about an order status,
			   use the getOrderStatus tool.

			2. When the customer asks about refund eligibility,
			   use the checkRefundEligibility tool.

			3. Do not invent order information.

			4. Base your answer on information returned
			   by the available tools.

			5. Keep the final response concise and helpful.
			""")
	@UserMessage("""
			Customer request:

			{{request}}
			""")
	@Agent("Handles customer support using a predefined set of tools")
	String support(@V("request") String request);

	@ToolsSupplier
	static Object[] tools() {

		System.out.println();
		System.out.println("========================================");
		System.out.println("@ToolsSupplier invoked");
		System.out.println("Providing predefined Java tool objects:");
		System.out.println("  - OrderTools");
		System.out.println("  - RefundTools");
		System.out.println("========================================");

		return new Object[] { new OrderTools(), new RefundTools() };
	}

	@ChatModelSupplier
	static ChatModel chatModel() {

		return OllamaModels.chatModel();
	}
}
