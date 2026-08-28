package com.sample.app.agents;

import com.sample.app.enums.ContentType;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ContentRouter {

  @SystemMessage(
      """
			You are a content request classifier.

			Your only job is to classify a request into exactly one
			of the following categories:

			BLOG
			SOCIAL_MEDIA
			EMAIL
			UNKNOWN

			Classification rules:

			BLOG:
			Use when the user asks for a blog, article, technical article,
			long-form post, or blog post.

			SOCIAL_MEDIA:
			Use when the user asks for a LinkedIn post, Twitter/X post,
			Instagram post, Facebook post, or other social media content.

			EMAIL:
			Use when the user asks to write, draft, rephrase, or create an email.

			UNKNOWN:
			Use only when the request clearly does not belong to
			BLOG, SOCIAL_MEDIA, or EMAIL.

			Return only the category.
			Do not provide explanations.
			Do not return additional text.
			""")
  @UserMessage(
      """
			Classify this request:

			{{request}}
			""")
  @Agent(
      name = "contentRouter",
      description = "Classifies content requests",
      outputKey = "contentType")
  ContentType classify(@V("request") String request);
}
