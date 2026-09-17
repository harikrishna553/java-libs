package com.sample.app;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import com.sample.app.agents.ExpenseAnalyzer;
import com.sample.app.agents.ExpenseProcessor;
import com.sample.app.chatmodels.Models;
import com.sample.app.models.ExpenseAnalysis;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.workflow.HumanInTheLoop;
import dev.langchain4j.model.chat.ChatModel;

public class App {

	public static void main(String[] args) {

		ChatModel model = Models.baseModel();

		/*
		 * --------------------------------------------------------- 1. AI AGENT -
		 * EXPENSE ANALYZER ---------------------------------------------------------
		 */

		ExpenseAnalyzer expenseAnalyzer = AgenticServices.agentBuilder(ExpenseAnalyzer.class).chatModel(model)
				.outputKey("expenseAnalysis").build();

		/*
		 * --------------------------------------------------------- 2.
		 * HUMAN-IN-THE-LOOP AGENT
		 * ---------------------------------------------------------
		 */

		HumanInTheLoop managerApproval = AgenticServices.humanInTheLoopBuilder()

				.description("Requests manager approval for an employee expense")

				.outputKey("approval")

				.responseProvider(scope -> {

					System.out.println();
					System.out.println("=======================================");

					System.out.println("       MANAGER APPROVAL REQUIRED");

					System.out.println("=======================================");

					/*
					 * Read the result produced by the ExpenseAnalyzer from AgenticScope.
					 */

					ExpenseAnalysis expense = scope.readState("expenseAnalysis", null);

					if (expense == null) {

						throw new IllegalStateException("Expense analysis not found");
					}

					System.out.println();
					System.out.println("Item          : " + expense.item());

					System.out.println("Amount        : ₹" + expense.amount());

					System.out.println("Category      : " + expense.category());

					System.out.println("Risk          : " + expense.riskLevel());

					System.out.println("Justification : " + expense.justification());

					System.out.println();

					System.out.print("Approve this expense? (yes/no): ");

					return readApproval();
				})

				.build();

		/*
		 * --------------------------------------------------------- 3. AI AGENT -
		 * EXPENSE PROCESSOR ---------------------------------------------------------
		 */

		ExpenseProcessor expenseProcessor = AgenticServices.agentBuilder(ExpenseProcessor.class).chatModel(model)
				.outputKey("result").build();

		/*
		 * --------------------------------------------------------- 4. CREATE
		 * SEQUENTIAL AGENTIC WORKFLOW
		 * ---------------------------------------------------------
		 *
		 * ExpenseAnalyzer ↓ HumanInTheLoop ↓ ExpenseProcessor
		 */

		UntypedAgent expenseWorkflow = AgenticServices.sequenceBuilder()
				.subAgents(expenseAnalyzer, managerApproval, expenseProcessor).outputKey("result").build();

		/*
		 * --------------------------------------------------------- 5. INPUT
		 * ---------------------------------------------------------
		 */

		String request = """
				I need a MacBook Pro for application development.
				The laptop costs approximately ₹175,000.
				I need it for running containers, local LLMs,
				development environments and performance tests.
				""";

		System.out.println();
		System.out.println("EMPLOYEE REQUEST");
		System.out.println("----------------");
		System.out.println(request);

		/*
		 * --------------------------------------------------------- 6. START AGENTIC
		 * WORKFLOW ---------------------------------------------------------
		 */

		Object result = expenseWorkflow.invoke(Map.of("request", request));

		/*
		 * --------------------------------------------------------- 7. FINAL RESULT
		 * ---------------------------------------------------------
		 */

		System.out.println();

		System.out.println("=======================================");

		System.out.println("            FINAL RESULT");

		System.out.println("=======================================");

		System.out.println(result);
	}



	/*
	 * Read and validate human approval.
	 */
	private static String readApproval() {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		while (true) {

			try {

				String response = reader.readLine();

				if (response == null) {
					throw new IllegalStateException("No response received");
				}

				String normalized = response.trim().toLowerCase();

				if (normalized.equals("yes") || normalized.equals("y")) {

					return "APPROVED";
				}

				if (normalized.equals("no") || normalized.equals("n")) {

					return "REJECTED";
				}

				System.out.print("Please enter yes or no: ");

			} catch (IOException e) {

				throw new RuntimeException("Unable to read manager approval", e);
			}
		}
	}
}