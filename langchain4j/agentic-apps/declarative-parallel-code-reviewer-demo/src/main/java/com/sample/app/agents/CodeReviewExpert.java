package com.sample.app.agents;

import com.sample.app.model.CodeReviewComments;
import com.sample.app.model.RefactoringSuggestion;
import com.sample.app.model.RefactoringSuggestions;

import dev.langchain4j.agentic.declarative.AgentListenerSupplier;
import dev.langchain4j.agentic.declarative.Output;
import dev.langchain4j.agentic.declarative.ParallelAgent;
import dev.langchain4j.agentic.declarative.ParallelExecutor;
import dev.langchain4j.agentic.observability.AgentListener;
import dev.langchain4j.service.V;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public interface CodeReviewExpert {

    /**
     * Executes all code-review experts in parallel.
     *
     * All four sub-agents receive the same "code" input.
     */
    @ParallelAgent(
            outputKey = "codeReviewComments",
            subAgents = {
                    CodeQualityExpert.class,
                    SecurityExpert.class,
                    PerformanceExpert.class,
                    RefactoringExpert.class
            }
    )
    CodeReviewComments reviewCode(@V("code") String code);

    /**
     * Provides the executor used by the @ParallelAgent.
     *
     * Since we have four independent experts, a fixed thread pool
     * with four threads allows all four agents to execute concurrently.
     */
    @ParallelExecutor
    static Executor executor() {

        System.out.println("======================================");
        System.out.println("@ParallelExecutor invoked");
        System.out.println("Creating thread pool with 4 threads");
        System.out.println("Code review experts will run in parallel");
        System.out.println("======================================");

        return Executors.newFixedThreadPool(4);
    }

    /**
     * Combines outputs produced by all four parallel agents
     * into the final CodeReviewComments object.
     */
    @Output
    static CodeReviewComments combineReviews(
            @V("codeQualityComments")
            List<String> codeQualityComments,

            @V("codeSecurityComments")
            List<String> codeSecurityComments,

            @V("codePerformanceComments")
            List<String> codePerformanceComments,

            @V("refactoringSuggestions")
            RefactoringSuggestions refactoringState) {

        System.out.println();
        System.out.println("======================================");
        System.out.println("Combining Parallel Agent Results");
        System.out.println("======================================");

        List<RefactoringSuggestion> refactoringSuggestions =
                refactoringState != null
                        && refactoringState.getSuggestions() != null
                        ? refactoringState.getSuggestions()
                        : List.of();

        CodeReviewComments codeReviewComments =
                new CodeReviewComments();

        codeReviewComments.setCodeQualityComments(
                codeQualityComments != null
                        ? codeQualityComments
                        : List.of()
        );

        codeReviewComments.setCodeSecurityComments(
                codeSecurityComments != null
                        ? codeSecurityComments
                        : List.of()
        );

        codeReviewComments.setCodePerformanceComments(
                codePerformanceComments != null
                        ? codePerformanceComments
                        : List.of()
        );

        codeReviewComments.setRefactoringSuggestions(
                refactoringSuggestions
        );

        return codeReviewComments;
    }
    
    @AgentListenerSupplier
	static AgentListener listener() {
        return new AgentWorkflowListener();
    }

}