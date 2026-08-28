package com.sample.app;

import java.util.Scanner;

import com.sample.app.agents.StoryWorkflow;
import com.sample.app.config.OllamaConfig;
import com.sample.app.model.StoryResult;

import dev.langchain4j.agentic.AgenticServices;

public class App {

	public static void main(String[] args) {

		System.out.println("Enter Your Story Idea >");
		String storyIdea = null;
		try (Scanner scanner = new Scanner(System.in)) {
			storyIdea = scanner.nextLine().trim();
		}

		StoryWorkflow storyWorkflow = AgenticServices.createAgenticSystem(StoryWorkflow.class,
				OllamaConfig.getChatModel());
		StoryResult sotryResult = storyWorkflow.createStory(storyIdea);

		System.out.println("Original Story : ");
		System.out.println(sotryResult.getOriginalStory());

		System.out.println(
				"-----------------------------------------------------------------------------------------------");
		System.out.println("Edited Story : ");
		System.out.println(sotryResult.getEditedStory());
	}

}
