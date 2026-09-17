package com.sample.app.agents;

import com.sample.app.models.ExpenseAnalysis;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ExpenseAnalyzer {

	@SystemMessage("""
			You are an expense analysis agent.

			Your job is to analyze an employee expense request.

			Extract:
			- item
			- amount
			- category
			- risk level
			- short justification

			Risk rules:

			LOW:
			amount below 25000

			MEDIUM:
			amount from 25000 to 100000

			HIGH:
			amount above 100000

			Possible categories include:
			- IT Equipment
			- Travel
			- Training
			- Office Equipment
			- Software
			- Other
			""")

	@UserMessage("""
			Analyze the following employee expense request.

			Request:

			{{request}}
			""")

	@Agent("Analyzes an employee expense request and determines its category and risk")
	ExpenseAnalysis analyze(@V("request") String request);
}