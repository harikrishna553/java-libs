package com.sample.app;

import java.util.*;

/**
 * A simple JSON parser for demonstration purposes
 * This is a very basic implementation to show JSON-RPC without external dependencies
 * In production, use Jackson or GSON instead
 */
public class SimpleJsonParser {

    public static JsonValue parse(String json) {
        json = json.trim();
        if (json.startsWith("{")) {
            return parseObject(json);
        } else if (json.startsWith("[")) {
            return parseArray(json);
        } else if (json.startsWith("\"")) {
            return new JsonValue(json.substring(1, json.length() - 1), JsonType.STRING);
        } else if (json.equalsIgnoreCase("true")) {
            return new JsonValue(true, JsonType.BOOLEAN);
        } else if (json.equalsIgnoreCase("false")) {
            return new JsonValue(false, JsonType.BOOLEAN);
        } else if (json.equalsIgnoreCase("null")) {
            return new JsonValue(null, JsonType.NULL);
        } else {
            try {
                if (json.contains(".")) {
                    return new JsonValue(Double.parseDouble(json), JsonType.NUMBER);
                } else {
                    return new JsonValue(Integer.parseInt(json), JsonType.NUMBER);
                }
            } catch (NumberFormatException e) {
                return new JsonValue(null, JsonType.NULL);
            }
        }
    }

    private static JsonValue parseObject(String json) {
        Map<String, JsonValue> map = new HashMap<>();
        json = json.substring(1, json.length() - 1).trim();

        if (json.isEmpty()) {
            return new JsonValue(map, JsonType.OBJECT);
        }

        // Simple key-value parsing
        List<String> pairs = splitByComma(json);
        for (String pair : pairs) {
            int colonIndex = pair.indexOf(':');
            if (colonIndex > 0) {
                String key = pair.substring(0, colonIndex).trim();
                String value = pair.substring(colonIndex + 1).trim();

                // Remove quotes from key
                if (key.startsWith("\"") && key.endsWith("\"")) {
                    key = key.substring(1, key.length() - 1);
                }

                map.put(key, parse(value));
            }
        }

        return new JsonValue(map, JsonType.OBJECT);
    }

    private static JsonValue parseArray(String json) {
        List<JsonValue> list = new ArrayList<>();
        json = json.substring(1, json.length() - 1).trim();

        if (json.isEmpty()) {
            return new JsonValue(list, JsonType.ARRAY);
        }

        List<String> elements = splitByComma(json);
        for (String element : elements) {
            list.add(parse(element.trim()));
        }

        return new JsonValue(list, JsonType.ARRAY);
    }

    private static List<String> splitByComma(String s) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        int depth = 0;
        boolean inString = false;
        boolean escape = false;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (escape) {
                current.append(c);
                escape = false;
                continue;
            }

            if (c == '\\' && inString) {
                escape = true;
                current.append(c);
                continue;
            }

            if (c == '"') {
                inString = !inString;
            } else if (!inString) {
                if (c == '{' || c == '[') {
                    depth++;
                } else if (c == '}' || c == ']') {
                    depth--;
                }
            }

            if (c == ',' && depth == 0 && !inString) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }

        if (current.length() > 0) {
            result.add(current.toString());
        }

        return result;
    }
}
