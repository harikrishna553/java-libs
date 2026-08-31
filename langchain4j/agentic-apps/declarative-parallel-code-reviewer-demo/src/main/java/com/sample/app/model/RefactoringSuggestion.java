package com.sample.app.model;

import dev.langchain4j.model.output.structured.Description;

public class RefactoringSuggestion {

  @Description(
      """
			A short, specific description of the code issue that should be
			addressed through refactoring. For example: "Method has too many
			responsibilities" or "Duplicate validation logic".
			""")
  private String issue;

  @Description(
      """
			A clear explanation of why the code should be refactored and what
			design, readability, maintainability, or simplicity problem it
			causes. Do not describe security or performance issues.
			""")
  private String explanation;

  @Description(
      """
			The exact relevant portion of the original Java source code that
			contains the identified refactoring opportunity. Preserve the
			original code without modifications.
			""")
  private String originalSnippet;

  @Description(
      """
			The improved version of the originalSnippet after applying the
			proposed refactoring. The code must be valid Java and should
			address the issue identified above while preserving the original
			behavior as much as possible.
			""")
  private String refactoredSnippet;

  public String getIssue() {
    return issue;
  }

  public void setIssue(String issue) {
    this.issue = issue;
  }

  public String getExplanation() {
    return explanation;
  }

  public void setExplanation(String explanation) {
    this.explanation = explanation;
  }

  public String getOriginalSnippet() {
    return originalSnippet;
  }

  public void setOriginalSnippet(String originalSnippet) {
    this.originalSnippet = originalSnippet;
  }

  public String getRefactoredSnippet() {
    return refactoredSnippet;
  }

  public void setRefactoredSnippet(String refactoredSnippet) {
    this.refactoredSnippet = refactoredSnippet;
  }
}
