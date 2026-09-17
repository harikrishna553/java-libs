package com.sample.app.agents;


import com.sample.app.models.ExpenseAnalysis;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ExpenseProcessor {

    @SystemMessage("""
            You process employee expense requests.

            The expense has already been analyzed
            and a manager has provided an approval decision.

            If approval is YES:
            confirm that the expense can proceed.

            If approval is NO:
            clearly state that the expense request was rejected.

            Do not override the manager's decision.
            """)

    @UserMessage("""
            Expense:

            {{expenseAnalysis}}

            Manager decision:

            {{approval}}

            Generate the final expense processing result.
            """)

    @Agent(
            "Processes an expense after the manager has made the approval decision"
    )
    String process(
            @V("expenseAnalysis") ExpenseAnalysis expenseAnalysis,
            @V("approval") String approval
    );
}