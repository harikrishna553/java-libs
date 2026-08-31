package com.sample.app.model;

import java.util.List;

public class CodeReviewComments {
  private List<String> codeQualityComments;
  private List<String> codeSecurityComments;
  private List<String> codePerformanceComments;
  private List<RefactoringSuggestion> refactoringSuggestions;

  public List<String> getCodeQualityComments() {
    return codeQualityComments;
  }

  public void setCodeQualityComments(List<String> codeQualityComments) {
    this.codeQualityComments = codeQualityComments;
  }

  public List<String> getCodeSecurityComments() {
    return codeSecurityComments;
  }

  public void setCodeSecurityComments(List<String> codeSecurityComments) {
    this.codeSecurityComments = codeSecurityComments;
  }

  public List<String> getCodePerformanceComments() {
    return codePerformanceComments;
  }

  public void setCodePerformanceComments(List<String> codePerformanceComments) {
    this.codePerformanceComments = codePerformanceComments;
  }

  public List<RefactoringSuggestion> getRefactoringSuggestions() {
    return refactoringSuggestions;
  }

  public void setRefactoringSuggestions(List<RefactoringSuggestion> refactoringSuggestions) {
    this.refactoringSuggestions = refactoringSuggestions;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("Code Review Comments\n");
    sb.append("====================\n");

    appendComments(sb, "Code Quality", codeQualityComments);
    appendComments(sb, "Code Security", codeSecurityComments);
    appendComments(sb, "Code Performance", codePerformanceComments);

    sb.append("\nRefactoring Suggestions:\n");
    sb.append("------------------------\n");

    if (refactoringSuggestions == null || refactoringSuggestions.isEmpty()) {
      sb.append("  No refactoring suggestions\n");
    } else {
      for (int i = 0; i < refactoringSuggestions.size(); i++) {
        RefactoringSuggestion suggestion = refactoringSuggestions.get(i);

        sb.append("\n  Suggestion ").append(i + 1).append(":\n");
        sb.append("    Issue:\n");
        sb.append("      ").append(suggestion.getIssue()).append("\n");

        sb.append("    Explanation:\n");
        sb.append("      ").append(suggestion.getExplanation()).append("\n");

        sb.append("    Original Code:\n");
        appendCode(sb, suggestion.getOriginalSnippet());

        sb.append("    Refactored Code:\n");
        appendCode(sb, suggestion.getRefactoredSnippet());
      }
    }

    return sb.toString();
  }

  private void appendComments(StringBuilder sb, String section, List<String> comments) {

    sb.append("\n").append(section).append(":\n");
    sb.append("-".repeat(section.length() + 1)).append("\n");

    if (comments == null || comments.isEmpty()) {
      sb.append("  No comments\n");
      return;
    }

    for (int i = 0; i < comments.size(); i++) {
      sb.append("  ").append(i + 1).append(". ").append(comments.get(i)).append("\n");
    }
  }

  private void appendCode(StringBuilder sb, String code) {
    if (code == null || code.isBlank()) {
      sb.append("      No code provided\n");
      return;
    }

    String[] lines = code.split("\\R");

    for (String line : lines) {
      sb.append("      ").append(line).append("\n");
    }
  }
}
