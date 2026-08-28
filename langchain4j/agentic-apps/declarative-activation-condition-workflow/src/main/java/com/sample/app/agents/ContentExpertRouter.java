package com.sample.app.agents;

import com.sample.app.enums.ContentType;
import dev.langchain4j.agentic.declarative.ActivationCondition;
import dev.langchain4j.agentic.declarative.ConditionalAgent;
import dev.langchain4j.service.V;

public interface ContentExpertRouter {

  @ConditionalAgent(
      name = "contentExpertRouter",
      outputKey = "content",
      subAgents = {
        BlogExpert.class,
        SocialMediaExpert.class,
        EmailExpert.class,
        UnknownContentExpert.class
      })
  String createContent(@V("request") String request);

  @ActivationCondition(BlogExpert.class)
  static boolean activateBlogExpert(@V("contentType") ContentType contentType) {

    return contentType == ContentType.BLOG;
  }

  @ActivationCondition(SocialMediaExpert.class)
  static boolean activateSocialMediaExpert(@V("contentType") ContentType contentType) {

    return contentType == ContentType.SOCIAL_MEDIA;
  }

  @ActivationCondition(EmailExpert.class)
  static boolean activateEmailExpert(@V("contentType") ContentType contentType) {

    return contentType == ContentType.EMAIL;
  }

  @ActivationCondition(UnknownContentExpert.class)
  static boolean activateUnknownContentExpert(@V("contentType") ContentType contentType) {

    return contentType == ContentType.UNKNOWN;
  }
}
