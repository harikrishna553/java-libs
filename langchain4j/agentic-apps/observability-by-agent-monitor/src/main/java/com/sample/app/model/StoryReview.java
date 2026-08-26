package com.sample.app.model;

import dev.langchain4j.model.output.structured.Description;
import java.util.Objects;

public class StoryReview {

  @Description("Overall quality rating of the story, integer from 1 to 10")
  private int rating;

  @Description(
      "Concise, actionable feedback for improving the story; use a single line with no embedded newlines")
  private String feedback;

  public StoryReview() {}

  public StoryReview(int rating, String feedback) {
    this.rating = rating;
    this.feedback = feedback;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getFeedback() {
    return feedback;
  }

  public void setFeedback(String feedback) {
    this.feedback = feedback;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof StoryReview)) return false;
    StoryReview that = (StoryReview) o;
    return rating == that.rating && Objects.equals(feedback, that.feedback);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rating, feedback);
  }

  @Override
  public String toString() {
    return "Quality rating: " + rating + "/10\nFeedback: " + feedback;
  }
}
