package com.sample.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import com.sample.app.agents.CodeReviewExpert;
import com.sample.app.config.OllamaConfig;
import com.sample.app.model.CodeReviewComments;

import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.model.chat.ChatModel;

public class App {

	public static String readFileToString(String codeFilePath) throws IOException {
		if (codeFilePath == null || codeFilePath.isEmpty()) {
			System.out.println("Code File path should not be empty");
			System.exit(1);
		}

		return Files.readString(Path.of(codeFilePath));
	}

	public static void main(String[] args) throws IOException {
		System.out.println("Enter the code file path to review the code");

		String codeToReview = null;

		try (Scanner scanner = new Scanner(System.in)) {
			String codeFilePath = scanner.nextLine().trim();
			codeToReview = readFileToString(codeFilePath);
		}

		ChatModel chatModel = OllamaConfig.getChatModel();

		CodeReviewExpert codeReviewExpert = AgenticServices.createAgenticSystem(CodeReviewExpert.class, chatModel);

		CodeReviewComments codeReviewComments = codeReviewExpert.reviewCode(codeToReview);

		System.out.println(codeReviewComments);
	}
}
