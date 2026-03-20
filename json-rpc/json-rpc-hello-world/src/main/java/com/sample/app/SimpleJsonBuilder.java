package com.sample.app;

import java.util.*;

/**
 * Helper class for building JSON objects
 */
public class SimpleJsonBuilder {
    private Map<String, JsonValue> map = new HashMap<>();

    public SimpleJsonBuilder add(String key, Object value) {
        if (value instanceof String) {
            map.put(key, new JsonValue(value, JsonType.STRING));
        } else if (value instanceof Number) {
            map.put(key, new JsonValue(value, JsonType.NUMBER));
        } else if (value instanceof Boolean) {
            map.put(key, new JsonValue(value, JsonType.BOOLEAN));
        } else {
            map.put(key, new JsonValue(null, JsonType.NULL));
        }
        return this;
    }

    public SimpleJsonBuilder addValue(String key, JsonValue value) {
        map.put(key, value);
        return this;
    }

    public SimpleJsonBuilder addNull(String key) {
        map.put(key, new JsonValue(null, JsonType.NULL));
        return this;
    }

    public JsonValue build() {
        return new JsonValue(map, JsonType.OBJECT);
    }
}
