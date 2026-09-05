package com.sample.app;

import com.sample.app.agents.DynamicToolsCustomerSupportAgent;
import com.sample.app.agents.StaticToolsCustomerSupportAgent;

import dev.langchain4j.agentic.AgenticServices;

public class App {

	public static void main(String[] args) {

		printIntroduction();

		/*
		 * ===================================================== DEMO 1
		 * 
		 * @ToolsSupplier =====================================================
		 */

		runToolsSupplierDemo();

		/*
		 * ===================================================== DEMO 2
		 * 
		 * @ToolProviderSupplier =====================================================
		 */

		runToolProviderSupplierDemo();

		printSummary();
	}

	private static void runToolsSupplierDemo() {

		System.out.println();
		System.out.println();
		System.out.println("##########################################");

		System.out.println(" DEMO 1 - @ToolsSupplier");

		System.out.println("##########################################");

		StaticToolsCustomerSupportAgent agent = AgenticServices
				.createAgenticSystem(StaticToolsCustomerSupportAgent.class);

		String question = "Where is order ORD-1001?";

		System.out.println();
		System.out.println("USER:");

		System.out.println(question);

		String response = agent.support(question);

		System.out.println();
		System.out.println("AGENT:");

		System.out.println(response);
	}

	private static void runToolProviderSupplierDemo() {

		System.out.println();
		System.out.println();
		System.out.println("##########################################");

		System.out.println(" DEMO 2 - @ToolProviderSupplier");

		System.out.println("##########################################");

		DynamicToolsCustomerSupportAgent agent = AgenticServices
				.createAgenticSystem(DynamicToolsCustomerSupportAgent.class);

		/*
		 * ----------------------------------------------------- REQUEST 1 Only Order
		 * Tool should be provided -----------------------------------------------------
		 */

		String orderQuestion = "Where is order ORD-1002?";

		System.out.println();
		System.out.println("USER:");

		System.out.println(orderQuestion);

		String orderResponse = agent.support(orderQuestion);

		System.out.println();
		System.out.println("AGENT:");

		System.out.println(orderResponse);

		/*
		 * ----------------------------------------------------- REQUEST 2 Refund Tool
		 * should be selected -----------------------------------------------------
		 */

		String refundQuestion = "Can I get a refund for ORD-1003?";

		System.out.println();
		System.out.println();
		System.out.println("USER:");

		System.out.println(refundQuestion);

		String refundResponse = agent.support(refundQuestion);

		System.out.println();
		System.out.println("AGENT:");

		System.out.println(refundResponse);

		/*
		 * ----------------------------------------------------- REQUEST 3 Both tools
		 * may be relevant -----------------------------------------------------
		 */

		String combinedQuestion = """
				What is the status of ORD-1001,
				and is it eligible for a refund?
				""";

		System.out.println();
		System.out.println();
		System.out.println("USER:");

		System.out.println(combinedQuestion);

		String combinedResponse = agent.support(combinedQuestion);

		System.out.println();
		System.out.println("AGENT:");

		System.out.println(combinedResponse);
	}

	private static void printIntroduction() {

		System.out.println("==========================================");

		System.out.println("   LangChain4j Declarative Tool Demo");

		System.out.println("==========================================");

		System.out.println();

		System.out.println("This application demonstrates:");

		System.out.println("  1. @ToolsSupplier");

		System.out.println("  2. @ToolProviderSupplier");

		System.out.println();

		System.out.println("@ToolsSupplier:");

		System.out.println("  Provides a predefined set of Java tools.");

		System.out.println();

		System.out.println("@ToolProviderSupplier:");

		System.out.println("  Provides a ToolProvider that decides which");

		System.out.println("  tools should be available for each request.");

		System.out.println();

		System.out.println("Available business tools:");

		System.out.println("  - getOrderStatus");

		System.out.println("  - checkRefundEligibility");
	}

	private static void printSummary() {

		System.out.println();
		System.out.println();
		System.out.println("==========================================");

		System.out.println("              Demo Complete");

		System.out.println("==========================================");

		System.out.println();

		System.out.println("@ToolsSupplier");

		System.out.println("  Agent receives predefined tool objects.");

		System.out.println();

		System.out.println("@ToolProviderSupplier");

		System.out.println("  Agent receives a ToolProvider.");

		System.out.println("  The provider can determine which tools");

		System.out.println("  are relevant for the current invocation.");
	}
}