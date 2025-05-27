package com.sample.app.streaming;

import static dev.langchain4j.model.LambdaStreamingResponseHandler.onPartialResponseAndError;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import dev.langchain4j.model.ollama.OllamaStreamingChatModel;

public class StreamingCompactWayBothResponseAndError {

	public static void main(String[] args) throws InterruptedException {

		OllamaStreamingChatModel chatModel = OllamaStreamingChatModel.builder().baseUrl("http://localhost:11434")
				.modelName("llama3.2").temperature(0.3).timeout(Duration.ofMinutes(1)).build();

		String prompt = "Tell me some Interesting Fact About LLMs in maximum 30 words";

		chatModel.chat(prompt, onPartialResponseAndError(System.out::println, Throwable::printStackTrace));

		// Sleep for 2 minutes to get the complete response tokens
		TimeUnit.MINUTES.sleep(2);

	}

}
