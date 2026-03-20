package com.sample.app;

import java.util.List;
import java.util.Map;

public class JsonValue {
    private Object value;
    private JsonType type;

    public JsonValue(Object value, JsonType type) {
        this.value = value;
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public JsonType getType() {
        return type;
    }

    public String asString() {
        return value != null ? value.toString() : "";
    }

    public double asDouble() {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return 0.0;
    }

    public int asInt() {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return 0;
    }

    public boolean asBoolean() {
        return value instanceof Boolean && (Boolean) value;
    }

    public Map<String, JsonValue> asObject() {
        return (Map<String, JsonValue>) value;
    }

    public List<JsonValue> asArray() {
        return (List<JsonValue>) value;
    }

    public boolean isNull() {
        return type == JsonType.NULL;
    }

    public String toJsonString() {
        switch (type) {
            case NULL:
                return "null";
            case BOOLEAN:
            case NUMBER:
                return value.toString();
            case STRING:
                return "\"" + escapeString(value.toString()) + "\"";
            case ARRAY:
                List<JsonValue> arr = asArray();
                StringBuilder arrStr = new StringBuilder("[");
                for (int i = 0; i < arr.size(); i++) {
                    if (i > 0) arrStr.append(",");
                    arrStr.append(arr.get(i).toJsonString());
                }
                arrStr.append("]");
                return arrStr.toString();
            case OBJECT:
                Map<String, JsonValue> obj = asObject();
                StringBuilder objStr = new StringBuilder("{");
                boolean first = true;
                for (Map.Entry<String, JsonValue> entry : obj.entrySet()) {
                    if (!first) objStr.append(",");
                    objStr.append("\"").append(entry.getKey()).append("\":");
                    objStr.append(entry.getValue().toJsonString());
                    first = false;
                }
                objStr.append("}");
                return objStr.toString();
            default:
                return "";
        }
    }

    private static String escapeString(String s) {
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
