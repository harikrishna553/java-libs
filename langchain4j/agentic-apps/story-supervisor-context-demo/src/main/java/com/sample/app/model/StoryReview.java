package com.sample.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.langchain4j.model.output.structured.Description;

@Description("Structured evaluation of a story")
public class StoryReview {

  @JsonProperty(required = true)
  @Description(
      """
			Quality score of the story.
			Must be an integer between 1 and 10.
			Example: 7
			""")
  private int score;

  @JsonProperty(required = true)
  @Description(
      """
            True only when score is greater than or equal
            to the requested quality threshold.
            """)
  private boolean thresholdReached;

  @JsonProperty(required = true)
  @Description(
      """
             Specific reviewer feedback describing strengths
             and improvements needed.
             """)
  private String feedback;

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public boolean isThresholdReached() {
    return thresholdReached;
  }

  public void setThresholdReached(boolean thresholdReached) {
    this.thresholdReached = thresholdReached;
  }

  public String getFeedback() {
    return feedback;
  }

  public void setFeedback(String feedback) {
    this.feedback = feedback;
  }
}
