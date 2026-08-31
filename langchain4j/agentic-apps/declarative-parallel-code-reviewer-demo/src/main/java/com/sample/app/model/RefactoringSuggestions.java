package com.sample.app.model;

import java.util.List;

public class RefactoringSuggestions {
  private List<RefactoringSuggestion> suggestions;

  public List<RefactoringSuggestion> getSuggestions() {
    return suggestions;
  }

  public void setSuggestions(List<RefactoringSuggestion> suggestions) {
    this.suggestions = suggestions;
  }
}
