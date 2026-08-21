package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface AudienceEditor {

  @UserMessage(
      """
        Rewrite the following story so that it is appropriate for the specified audience.

        Story:
        {{story}}

        Target audience:
        {{audience}}
        """)
  @Agent("Edits the story for the target audience")
  String edit(@V("story") String story, @V("audience") String audience);
}
