package com.sample.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtil {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private static final ObjectWriter prettyWriter = objectMapper.writerWithDefaultPrettyPrinter();

	/**
	 * Converts an object to a pretty-printed JSON string.
	 */
	public static String toPrettyJson(Object obj) {
		try {
			return prettyWriter.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to convert to pretty JSON", e);
		}
	}

	/**
	 * Converts a JSON string to a pretty-printed JSON string.
	 */
	public static String prettyPrintJsonString(String json) {
		try {
			Object jsonObject = objectMapper.readValue(json, Object.class);
			return toPrettyJson(jsonObject);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Invalid JSON input", e);
		}
	}

	/**
	 * Converts an object to a compact (non-pretty) JSON string.
	 */
	public static String toCompactJson(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to convert to compact JSON", e);
		}
	}
}
