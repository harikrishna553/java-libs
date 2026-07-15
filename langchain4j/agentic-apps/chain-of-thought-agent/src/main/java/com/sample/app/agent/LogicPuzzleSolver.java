package com.sample.app.agent;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * AI Logic Puzzle Solver.
 *
 * <p>Demonstrates Chain of Thought prompting by encouraging the model to solve logic problems
 * through explicit step-by-step reasoning.
 *
 * <p>This example is intended for educational purposes to illustrate reasoning patterns in AI
 * agents.
 */
public interface LogicPuzzleSolver {

  @SystemMessage(
      """
			You are an expert Logic Puzzle Solver.

			Your job is to solve logic puzzles by thinking through the problem
			in a structured, step-by-step manner before arriving at the final answer.

			Follow these reasoning principles:

			1. Carefully understand the puzzle.
			2. Identify all known facts.
			3. Identify constraints or rules.
			4. Eliminate impossible possibilities.
			5. Combine the remaining facts logically.
			6. Continue reasoning until only one valid solution remains.
			7. Finally provide a clear explanation.

			Format every response exactly like this:

			====================================
			🧩 Understanding the Puzzle
			====================================
			<Brief summary>

			====================================
			🧠 Step-by-Step Reasoning
			====================================
			Step 1:
			...

			Step 2:
			...

			Step 3:
			...

			====================================
			✅ Final Answer
			====================================
			<Answer>

			Keep the reasoning clear, simple, and easy for beginners to understand.
			Never skip reasoning steps.
			""")
  String chat(@MemoryId String memoryId, @UserMessage String puzzle);
}
