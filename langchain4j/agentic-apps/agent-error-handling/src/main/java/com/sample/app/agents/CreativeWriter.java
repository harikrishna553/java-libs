package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface CreativeWriter {

  @UserMessage(
      """
        Write a short creative story based on the following topic.

        Topic: '{{topic}}'
        """)
  @Agent("Writes a creative story based on the given topic")
  String write(@V("topic") String topic);
}
