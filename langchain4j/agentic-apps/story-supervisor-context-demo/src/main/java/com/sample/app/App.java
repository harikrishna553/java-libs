package com.sample.app;

import com.sample.app.agents.StoryCreatorAgent;
import com.sample.app.agents.StoryEditorAgent;
import com.sample.app.agents.StoryReviewerAgent;
import com.sample.app.agents.StorySupervisor;
import com.sample.app.listeners.ConsoleAgentListener;
import com.sample.app.models.Models;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.model.chat.ChatModel;
import java.util.Scanner;

public class App {

  private static final int MAX_AGENT_INVOCATIONS = 12;

  public static void main(String[] args) {

    ChatModel baseModel = Models.baseModel();
    ChatModel plannerModel = Models.plannerModel();

    /*
     * ------------------------------------------------------------ Story Creator
     * ------------------------------------------------------------
     */

    StoryCreatorAgent storyCreator =
        AgenticServices.agentBuilder(StoryCreatorAgent.class)
            .name("storyCreator")
            .chatModel(baseModel)
            .outputKey("story")
            .build();

    /*
     * ------------------------------------------------------------ Story Editor
     * ------------------------------------------------------------
     */

    StoryEditorAgent storyEditor =
        AgenticServices.agentBuilder(StoryEditorAgent.class)
            .name("storyEditor")
            .chatModel(baseModel)
            .outputKey("story")
            .build();

    /*
     * ------------------------------------------------------------ Story Reviewer
     * ------------------------------------------------------------
     */

    StoryReviewerAgent storyReviewer =
        AgenticServices.agentBuilder(StoryReviewerAgent.class)
            .name("storyReviewer")
            .chatModel(baseModel)
            .outputKey("review")
            .build();

    /*
     * ------------------------------------------------------------ User Input
     * ------------------------------------------------------------
     */

    Scanner scanner = new Scanner(System.in);

    System.out.println();
    System.out.println("==========================================");
    System.out.println("     STORY SUPERVISOR DEMO");
    System.out.println("==========================================");

    System.out.println();
    System.out.print("Enter your story idea: ");

    String storyIdea = scanner.nextLine().trim();

    if (storyIdea.isBlank()) {
      throw new IllegalArgumentException("Story idea cannot be empty");
    }

    System.out.print("Enter quality threshold (1-10): ");

    int qualityThreshold = Integer.parseInt(scanner.nextLine().trim());

    if (qualityThreshold < 1 || qualityThreshold > 10) {

      throw new IllegalArgumentException("Quality threshold must be between 1 and 10");
    }

    /*
     * ------------------------------------------------------------ Dynamic
     * Supervisor Context
     * ------------------------------------------------------------
     *
     * This is invocation-specific context.
     *
     * The quality threshold supplied by the user becomes part of the Supervisor's
     * planning guidance.
     */

    String supervisorContext = createSupervisorContext(qualityThreshold);

    System.out.println();
    System.out.println("==========================================");
    System.out.println("SUPERVISOR CONTEXT");
    System.out.println("==========================================");
    System.out.println(supervisorContext);

    /*
     * ------------------------------------------------------------ Story Supervisor
     * ------------------------------------------------------------
     *
     * Notice:
     *
     * There is NO:
     *
     * while (score < threshold)
     *
     * loop here.
     *
     * The Supervisor is responsible for deciding which agent should execute next.
     */

    StorySupervisor storySupervisor =
        AgenticServices.supervisorBuilder(StorySupervisor.class)
            .name("storySupervisor")
            .chatModel(plannerModel)
            .subAgents(storyCreator, storyEditor, storyReviewer)
            .maxAgentsInvocations(MAX_AGENT_INVOCATIONS)

            /*
             * Regardless of which agent happened to execute last, return the latest value
             * stored under "story" in the AgenticScope.
             */
            .output(scope -> scope.readState("story", "No story was generated."))

            /*
             * This listener is inherited by the subagents, allowing us to observe
             * Supervisor decisions.
             */
            .listener(new ConsoleAgentListener())
            .supervisorContext(supervisorContext)
            .build();

    /*
     * ------------------------------------------------------------ Invoke
     * Supervisor ------------------------------------------------------------
     */

    String finalStory = storySupervisor.createStory(storyIdea, qualityThreshold);

    /*
     * ------------------------------------------------------------ Result
     * ------------------------------------------------------------
     */

    System.out.println();
    System.out.println("==========================================");
    System.out.println("FINAL STORY");
    System.out.println("==========================================");
    System.out.println();
    System.out.println(finalStory);
  }

  private static String createSupervisorContext(int qualityThreshold) {

    return """
				You are supervising a storytelling team.

				Available specialist agents:

				1. storyCreator
				   Creates the initial story.

				2. storyReviewer
				   Reviews the current story and produces:
				   - quality score
				   - thresholdReached
				   - improvement feedback

				3. storyEditor
				   Improves an existing story using the
				   latest reviewer feedback.

				STORY POLICIES:

				- Stories must be family-friendly.
				- Target audience is children aged 8-10.
				- Use simple and engaging English.
				- Prefer a fun and adventurous tone.
				- Avoid graphic violence.
				- Keep the story reasonably concise.
				- The story should have a clear beginning,
				  middle, and ending.

				QUALITY TARGET:

				The required quality score is %d out of 10.

				SUPERVISION RULES:

				- If no story exists yet, invoke storyCreator.

				- Once a story exists, invoke storyReviewer
				  to evaluate it.

				- Carefully inspect the StoryReview returned
				  by storyReviewer.

				- If thresholdReached is false, the story
				  is NOT finished.

				- When thresholdReached is false, invoke
				  storyEditor using the latest review feedback.

				- After storyEditor improves the story,
				  invoke storyReviewer again.

				- Continue the review/improvement cycle while
				  meaningful improvements are required.

				- Do not invoke storyCreator again merely to
				  improve an existing story. Use storyEditor.

				- Do not finish the task when the latest
				  StoryReview has thresholdReached=false.

				- Finish when the latest StoryReview has
				  thresholdReached=true.

				- Once the requested quality threshold has
				  been reached, do not perform unnecessary
				  additional editing.

				The goal is to produce the best final story
				while reaching the requested quality threshold.
				"""
        .formatted(qualityThreshold);
  }
}
