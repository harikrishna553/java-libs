package com.sample.app.llm;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.app.model.PlanResponse;
import com.sample.app.model.PlanStep;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Parses the raw LLM text response into a structured {@link PlanResponse}.
 *
 * <p>Local LLMs (e.g., Llama 3.2 via Ollama) often wrap JSON in markdown code fences or prefix it
 * with conversational text, even when instructed not to. This parser handles those cases
 * defensively before deserializing the JSON.
 *
 * <p>Parsing strategy:
 *
 * <ol>
 *   <li>Strip markdown code fences (```json ... ``` or ``` ... ```)
 *   <li>Extract the first complete JSON object ({@code { ... }}) from the text
 *   <li>Use Jackson's {@link JsonNode} API to extract fields explicitly
 *   <li>Return a fallback {@link PlanResponse} if parsing fails entirely
 * </ol>
 *
 * <p>Using {@link JsonNode} (rather than direct POJO mapping) keeps the parsing visible and
 * step-by-step — consistent with the educational goal of this project.
 */
public class PlanParser {

  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Parses a raw LLM response string into a {@link PlanResponse}.
   *
   * <p>If parsing fails for any reason, returns a fallback response with status {@code IN_PROGRESS}
   * and no next step, which will cause the orchestrator to surface the error rather than silently
   * continuing.
   *
   * @param rawResponse the raw text returned by the LLM
   * @return the parsed PlanResponse, or a safe fallback if parsing fails
   */
  public PlanResponse parse(String rawResponse) {
    try {
      String cleaned = extractJson(rawResponse);
      JsonNode root = objectMapper.readTree(cleaned);

      String status = readString(root, "status", PlanResponse.STATUS_IN_PROGRESS);

      JsonNode nextStepNode = root.get("nextStep");

      if (nextStepNode == null || nextStepNode.isNull()) {
        return new PlanResponse(status, null);
      }

      String toolName = readString(nextStepNode, "toolName", "");
      String reasoning = readString(nextStepNode, "reasoning", "");
      Map<String, String> parameters = readStringMap(nextStepNode, "parameters");

      PlanStep planStep = new PlanStep(toolName, parameters, reasoning);
      return new PlanResponse(status, planStep);

    } catch (Exception e) {
      // Return a safe fallback; the orchestrator will print the raw response for debugging
      return new PlanResponse(
          "PARSE_ERROR",
          new PlanStep("_parse_error_", Map.of("rawResponse", rawResponse), e.getMessage()));
    }
  }

  /**
   * Extracts the first complete JSON object from a string that may contain surrounding prose or
   * markdown code fences.
   *
   * @param raw the raw text that may contain JSON
   * @return the extracted JSON substring
   * @throws IllegalArgumentException if no JSON object is found
   */
  private String extractJson(String raw) {
    if (raw == null || raw.isBlank()) {
      throw new IllegalArgumentException("LLM returned empty response");
    }

    // Step 1: Strip markdown code fences
    String cleaned = raw.trim();
    if (cleaned.startsWith("```")) {
      int firstNewline = cleaned.indexOf('\n');
      if (firstNewline != -1) {
        cleaned = cleaned.substring(firstNewline + 1);
      }
      if (cleaned.endsWith("```")) {
        cleaned = cleaned.substring(0, cleaned.lastIndexOf("```"));
      }
      cleaned = cleaned.trim();
    }

    // Step 2: Find the first '{' and matching '}' to extract a JSON object
    int start = cleaned.indexOf('{');
    if (start == -1) {
      throw new IllegalArgumentException("No JSON object found in response: " + raw);
    }

    int depth = 0;
    int end = -1;
    for (int i = start; i < cleaned.length(); i++) {
      char c = cleaned.charAt(i);
      if (c == '{') depth++;
      else if (c == '}') {
        depth--;
        if (depth == 0) {
          end = i;
          break;
        }
      }
    }

    if (end == -1) {
      throw new IllegalArgumentException("Unterminated JSON object in response: " + raw);
    }

    return cleaned.substring(start, end + 1);
  }

  /**
   * Safely reads a string field from a JsonNode, returning a default if absent.
   *
   * @param node the JSON node to read from
   * @param fieldName the field name to look up
   * @param defaultValue value to return if field is absent or null
   * @return the field value as a String
   */
  private String readString(JsonNode node, String fieldName, String defaultValue) {
    JsonNode field = node.get(fieldName);
    if (field == null || field.isNull()) {
      return defaultValue;
    }
    return field.asText(defaultValue);
  }

  /**
   * Reads a JSON object field as a {@code Map<String, String>}.
   *
   * @param node the parent JSON node
   * @param fieldName the field name of the nested object
   * @return a map of string key-value pairs (empty if field absent or not an object)
   */
  private Map<String, String> readStringMap(JsonNode node, String fieldName) {
    Map<String, String> result = new HashMap<>();
    JsonNode mapNode = node.get(fieldName);
    if (mapNode == null || !mapNode.isObject()) {
      return result;
    }
    Iterator<Map.Entry<String, JsonNode>> fields = mapNode.fields();
    while (fields.hasNext()) {
      Map.Entry<String, JsonNode> entry = fields.next();
      result.put(entry.getKey(), entry.getValue().asText(""));
    }
    return result;
  }
}
