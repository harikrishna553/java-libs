package com.sample.app.structured.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.app.dto.Employee;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.chat.request.ResponseFormatType;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.chat.request.json.JsonSchema;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class JsonSchemaDemo {

  public static void main(String[] args) throws JsonMappingException, JsonProcessingException {

    JsonObjectSchema employeeSchema =
        JsonObjectSchema.builder()
            .addStringProperty("name")
            .addIntegerProperty("age")
            .addNumberProperty("height")
            .addBooleanProperty("married")
            .required("name", "age", "height", "married")
            .build();

    ResponseFormat responseFormat =
        ResponseFormat.builder()
            .type(ResponseFormatType.JSON)
            .jsonSchema(JsonSchema.builder().name("Employee").rootElement(employeeSchema).build())
            .build();

    // Create a new user message with a different username and message
    UserMessage userMessage =
        UserMessage.from(
            """
				Alice is 30 years old and leads a vibrant lifestyle.
				She is 1.65 meters tall and always brings energy into any room.
				Though married, she balances her family life with an active professional career.
				""");

    ChatRequest chatRequest =
        ChatRequest.builder().responseFormat(responseFormat).messages(userMessage).build();

    ChatModel chatModel =
        OllamaChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3.2").build();

    ChatResponse chatResponse = chatModel.chat(chatRequest);

    String output = chatResponse.aiMessage().text();
    System.out.println(output);

    Employee person = new ObjectMapper().readValue(output, Employee.class);
    System.out.println(person);
  }
}
