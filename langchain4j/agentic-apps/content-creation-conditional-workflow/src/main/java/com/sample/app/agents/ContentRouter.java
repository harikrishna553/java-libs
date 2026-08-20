package com.sample.app.agents;

import com.sample.app.enums.ContentType;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ContentRouter {

  @UserMessage(
      """
            Analyze the following user request and categorize it as
            'blog', 'social_media' or 'email'.

            Use 'blog' when the user wants a blog post, article,
            tutorial, or long-form technical/content article.

            Use 'social_media' when the user wants content for
            LinkedIn, X/Twitter, Facebook, Instagram, or similar
            social media platforms.

            Use 'email' when the user wants an email, announcement,
            newsletter, or email communication.

            If the request doesn't belong to any of these categories,
            categorize it as 'unknown'.

            Reply with only one of these words and nothing else.

            The user request is: '{{request}}'.
            """)
  @Agent("Categorizes a content creation request")
  ContentType classify(@V("request") String request);
}
