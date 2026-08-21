package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StyleEditor {

  @UserMessage(
      """
        Improve the writing style of the following story.

        Story:
        {{story}}

        Desired style:
        {{style}}

        Return only the improved story.
        """)
  @Agent("Improves the story according to the requested style")
  TokenStream edit(@V("story") String story, @V("style") String style);
}
