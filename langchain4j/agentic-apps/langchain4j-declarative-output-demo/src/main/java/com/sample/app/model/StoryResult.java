package com.sample.app.model;

public class StoryResult {
	private String originalStory;
	private String editedStory;

	public StoryResult(String originalStory, String editedStory) {
		this.originalStory = originalStory;
		this.editedStory = editedStory;
	}

	public String getOriginalStory() {
		return originalStory;
	}

	public void setOriginalStory(String originalStory) {
		this.originalStory = originalStory;
	}

	public String getEditedStory() {
		return editedStory;
	}

	public void setEditedStory(String editedStory) {
		this.editedStory = editedStory;
	}

}
