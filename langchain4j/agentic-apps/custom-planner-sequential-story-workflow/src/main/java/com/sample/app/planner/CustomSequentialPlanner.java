package com.sample.app.planner;

import dev.langchain4j.agentic.planner.Action;
import dev.langchain4j.agentic.planner.AgentInstance;
import dev.langchain4j.agentic.planner.AgenticSystemTopology;
import dev.langchain4j.agentic.planner.InitPlanningContext;
import dev.langchain4j.agentic.planner.Planner;
import dev.langchain4j.agentic.planner.PlanningContext;

import java.util.List;
import java.util.Map;

public class CustomSequentialPlanner implements Planner {

    private static final String CURSOR_STATE_KEY = "cursor";

    /*
     * Subagents configured using:
     *
     * .subAgents(
     *     storyWriter,
     *     storyReviewer,
     *     storyEditor
     * )
     */
    private List<AgentInstance> agents;

    /*
     * Points to the next agent that should be executed.
     *
     * 0 -> StoryWriter
     * 1 -> StoryReviewer
     * 2 -> StoryEditor
     */
    private int agentCursor = 0;

    /**
     * Called when the Planner is initialized.
     *
     * The InitPlanningContext gives us access to all subagents
     * configured for this agentic system.
     */
    @Override
    public void init(InitPlanningContext initPlanningContext) {

        this.agents = initPlanningContext.subagents();

        System.out.println();
        System.out.println("======================================");
        System.out.println("Initializing CustomSequentialPlanner");
        System.out.println("======================================");

        for (int i = 0; i < agents.size(); i++) {

            AgentInstance agent = agents.get(i);

            System.out.printf(
                "%d -> %s%n",
                i,
                agent.name()
            );
        }

        System.out.println();
    }

    /**
     * firstAction() does not have to be overridden.
     *
     * Planner already provides:
     *
     * default Action firstAction(PlanningContext context) {
     *     return nextAction(context);
     * }
     *
     * Therefore both the first invocation and subsequent
     * invocations use the same cursor-based logic.
     */

    /**
     * Determine which agent should execute next.
     */
    @Override
    public Action nextAction(PlanningContext planningContext) {

        /*
         * No agents remaining.
         */
        if (terminated()) {

            System.out.println();
            System.out.println(
                "All agents executed. Workflow completed."
            );

            return done();
        }

        /*
         * Get the next agent.
         */
        AgentInstance nextAgent = agents.get(agentCursor);

        System.out.printf(
            "Planner decision: invoke agent [%d] -> %s%n",
            agentCursor,
            nextAgent.name()
        );

        /*
         * Move cursor so that the following invocation
         * selects the next agent.
         */
        agentCursor++;

        /*
         * Because only one AgentInstance is passed to call(),
         * the framework executes this agent sequentially.
         */
        return call(nextAgent);
    }

    /**
     * Indicates whether all configured agents have executed.
     */
    @Override
    public boolean terminated() {

        return agents != null
            && agentCursor >= agents.size();
    }

    /**
     * Our planner represents a sequential topology.
     */
    @Override
    public AgenticSystemTopology topology() {

        return AgenticSystemTopology.SEQUENCE;
    }

    /**
     * Persist enough planner state to support workflow recovery.
     *
     * We store the index of the last agent that was scheduled.
     *
     * If execution is interrupted while that agent is running,
     * restoring this cursor causes that agent to be executed again.
     */
    @Override
    public Map<String, Object> executionState() {

        if (agentCursor == 0) {
            return Map.of();
        }

        return Map.of(
            CURSOR_STATE_KEY,
            agentCursor - 1
        );
    }

    /**
     * Restore planner state during workflow recovery.
     */
    @Override
    public void restoreExecutionState(
        Map<String, Object> state
    ) {

        Object savedCursor =
            state.get(CURSOR_STATE_KEY);

        if (savedCursor instanceof Number number) {

            this.agentCursor =
                number.intValue();
        }
    }
}