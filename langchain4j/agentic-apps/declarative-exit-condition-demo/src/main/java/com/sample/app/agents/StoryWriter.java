package com.sample.app.agents;

import com.sample.app.config.OllamaConfig;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryWriter {

  @UserMessage(
      """
        You are the Story Writer in an iterative children's story
        creation workflow.

        Create a short, engaging children's story based on the user's
        story idea.

        Requirements:
        1. Use simple language suitable for children ages 5 to 10.
        2. Create interesting characters and a clear beginning, middle,
           and ending.
        3. Include a positive message or lesson.
        4. Make the story imaginative, warm, and engaging.
        5. Give the story a suitable title.

        Important:
        - Return only the story.
        - Do not include explanations or editorial comments.
        - Keep the story concise.

        Story idea:
        {{storyIdea}}
        """)
  @Agent(
      name = "storyWriter",
      description =
          """
            Creates an engaging children's story from a given story idea.
            Produces the initial story that will be evaluated and improved
            by subsequent agents in the iterative workflow.
            """,
      outputKey = "story")
  String writeStory(@V("storyIdea") String storyIdea);

  @ChatModelSupplier
  static ChatModel chatModel() {
    return OllamaConfig.getChatModel();
  }
}
