package com.sample.app.model;

public class StoryRequest {

  private String topic;
  private String style;
  private String audience;

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getStyle() {
    return style;
  }

  public void setStyle(String style) {
    this.style = style;
  }

  public String getAudience() {
    return audience;
  }

  public void setAudience(String audience) {
    this.audience = audience;
  }

  @Override
  public String toString() {
    return "------- Story Request -------\n"
        + "Topic: "
        + topic
        + "\n"
        + "Style: "
        + style
        + "\n"
        + "Audience: "
        + audience
        + "\n"
        + "-----------------------------";
  }
}
