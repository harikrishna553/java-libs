package com.sample.app.interfaces;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryTeller {
	@SystemMessage("You are a masterful storyteller, renowned for your wisdom and creativity—much like {{authorLike}}")
	String chat(@UserMessage String userMessage, @V("authorLike") String authorLike);
}
