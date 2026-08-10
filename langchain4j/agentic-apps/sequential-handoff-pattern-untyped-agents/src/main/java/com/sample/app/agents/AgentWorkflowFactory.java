package com.sample.app.agents;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.model.chat.ChatModel;

public final class AgentWorkflowFactory {

  private AgentWorkflowFactory() {}

  public static UntypedAgent createWorkflow(ChatModel chatModel) {

    // ------------------------------------------------------------
    // 1. Story Planner
    // ------------------------------------------------------------
    UntypedAgent storyPlanner =
        AgenticServices.agentBuilder()
            .name("storyPlanner")
            .description(
                """
				Plans the overall structure of a children's story.
				Takes the user's story idea and transforms it into a clear
				beginning, conflict, key events, climax, and ending.
				Hands the resulting story plot to the Character Designer.
				""")
            .chatModel(chatModel)
            .userMessage(
                """
				You are the Story Planner, the first agent in a sequential
				story-creation workflow.

				Your responsibility is to transform the user's simple story idea
				into a clear and engaging plot that the next agents can build upon.

				Follow these steps:
				1. Understand the central idea and intended theme.
				2. Define how the story begins and introduce the situation.
				3. Identify the main problem or conflict.
				4. Define the important events that move the story forward.
				5. Describe the climax or most important turning point.
				6. Define a satisfying and meaningful ending.

				Create the plot using exactly these headings:

				Beginning
				Main conflict
				Important events
				Climax
				Ending

				Guidelines:
				- Keep the story simple and easy to understand.
				- Make the plot suitable for children ages 5 to 10.
				- Keep the story warm, positive, and imaginative.
				- Avoid unnecessary characters or subplots.
				- Make sure the conflict has a clear resolution.
				- Do not write the complete story yet.
				- Focus only on creating the story blueprint for the next agent.

				Story idea:
				{{storyIdea}}

				Your output will be handed off to the Character Designer,
				who will use this plot to determine which characters are needed.
				""")
            .inputKey(String.class, "storyIdea")
            .outputKey("plot")
            .build();

    // ------------------------------------------------------------
    // 2. Character Designer
    // ------------------------------------------------------------
    UntypedAgent characterDesigner =
        AgenticServices.agentBuilder()
            .name("characterDesigner")
            .description(
                """
				Designs the characters needed to bring the planned story to life.
				Uses the original story idea and the plot received from the Story Planner
				to define a small cast with clear personalities and story roles.
				Hands the character descriptions to the Story Writer.
				""")
            .chatModel(chatModel)
            .userMessage(
                """
				You are the Character Designer, the second agent in a sequential
				story-creation workflow.

				The Story Planner has already created the story plot.
				Your responsibility is to design the characters needed to tell
				that story effectively.

				Read both the original story idea and the plot from the previous agent.

				For each important character, provide:

				Name
				Type/species
				Personality
				Role in the story

				Character design guidelines:
				- Create only the characters that are necessary for the plot.
				- Keep the cast small and easy for children to remember.
				- Give each character a distinct personality.
				- Make the characters suitable for children ages 5 to 10.
				- Make sure every character has a meaningful role in the story.
				- Ensure the characters support the conflict and events described
				  in the plot.
				- Avoid introducing characters that are not needed.
				- Do not write the story yet.

				Original story idea:
				{{storyIdea}}

				Plot created by the Story Planner:
				{{plot}}

				Your output will be handed off to the Story Writer.
				The Story Writer will use your character descriptions together
				with the plot to create the complete story draft.
				""")
            .inputKeys(String.class, "storyIdea", String.class, "plot")
            .outputKey("characters")
            .build();

    // ------------------------------------------------------------
    // 3. Story Writer
    // ------------------------------------------------------------
    UntypedAgent storyWriter =
        AgenticServices.agentBuilder()
            .name("storyWriter")
            .description(
                """
				Turns the story plan and character designs into a complete
				children's story draft.
				Combines the original idea, planned plot, and character profiles
				into a coherent narrative with a title, engaging events, and
				age-appropriate language.
				Hands the completed draft to the Story Editor for refinement.
				""")
            .chatModel(chatModel)
            .userMessage(
                """
				You are the Story Writer, the third agent in a sequential
				story-creation workflow.

				Two agents have already completed their work:
				- The Story Planner created the plot.
				- The Character Designer created the characters.

				Your responsibility is to combine their outputs with the original
				story idea and turn them into a complete, engaging children's story.

				Writing requirements:
				- Give the story a clear and appealing title.
				- Follow the plot created by the Story Planner.
				- Use the characters designed by the Character Designer.
				- Preserve the intended conflict, important events, climax,
				  and ending from the plot.
				- Give the characters distinct personalities and meaningful actions.
				- Use simple, friendly, and imaginative language.
				- Keep the story suitable for children ages 5 to 10.
				- Maintain a clear beginning, middle, and ending.
				- Make the story engaging without becoming unnecessarily long.
				- Do not introduce major characters or plot elements that conflict
				  with the previous agents' work.

				Important:
				You are creating a draft, not the final edited version.
				Focus on storytelling and narrative flow.
				Do not include explanations about your writing process.

				Story idea:
				{{storyIdea}}

				Plot from the Story Planner:
				{{plot}}

				Characters from the Character Designer:
				{{characters}}

				Your completed draft will be handed off to the Story Editor,
				who will review and polish it before it is presented as the
				final story.
				""")
            .inputKeys(String.class, "storyIdea", String.class, "plot", String.class, "characters")
            .outputKey("draft")
            .build();

    // ------------------------------------------------------------
    // 4. Story Editor
    // ------------------------------------------------------------
    UntypedAgent storyEditor =
        AgenticServices.agentBuilder()
            .name("storyEditor")
            .description(
                """
				Reviews and polishes the story draft created by the Story Writer.
				Improves grammar, clarity, consistency, pacing, and age appropriateness
				while preserving the original story's intent and creativity.
				Produces the final story that is ready for the reader.
				""")
            .chatModel(chatModel)
            .userMessage(
                """
				You are the Story Editor, the final agent in a sequential
				story-creation workflow.

				The Story Writer has created a complete children's story draft.
				Your responsibility is to review, refine, and polish that draft
				so it is ready for the final reader.

				Review the story carefully for:

				1. Grammar and language
				   Correct grammatical errors and awkward sentences.

				2. Clarity
				   Make sure events, characters, and ideas are easy to understand.

				3. Consistency
				   Ensure character names, personalities, events, and story details
				   remain consistent throughout the story.

				4. Pacing
				   Remove unnecessary repetition and make sure the story flows
				   naturally from beginning to ending.

				5. Age appropriateness
				   Ensure the vocabulary, themes, conflict, and writing style are
				   appropriate for children ages 5 to 10.

				6. Story quality
				   Preserve the imagination, warmth, lesson, and emotional appeal
				   of the original story.

				Important:
				- Do not completely rewrite the story unless necessary.
				- Preserve the Story Writer's core idea and narrative.
				- Do not introduce unrelated characters or events.
				- Do not add explanations, comments, or editorial notes.
				- Return only the polished final story.
				- Keep the title.

				Draft story from the Story Writer:
				{{draft}}

				This is the final stage of the workflow.
				Your output will be returned to the user as the final story.
				""")
            .inputKey(String.class, "draft")
            .outputKey("finalStory")
            .build();

    // ------------------------------------------------------------
    // Sequential Handoff Workflow
    // ------------------------------------------------------------
    return AgenticServices.sequenceBuilder()
        .name("sequentialStoryWorkflow")
        .description(
            """
				Creates a children's story through a deterministic sequence
				of specialized agents.

				Each agent completes one stage of the story creation process
				and hands its output to the next agent:

				Story Planner
				    ->
				Character Designer
				    ->
				Story Writer
				    ->
				Story Editor

				The workflow starts with a simple story idea and progressively
				transforms it into a polished final story.
				""")
        .subAgents(
            // Handoff 1: Story Planner -> Character Designer
            storyPlanner,

            // Handoff 2: Character Designer -> Story Writer
            characterDesigner,

            // Handoff 3: Story Writer -> Story Editor
            storyWriter,
            storyEditor)
        .listener(new AgentWorkflowListener())
        .outputKey("finalStory")
        .build();
  }
}
