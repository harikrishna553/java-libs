package com.sample.loghash.util;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonUtil {
	private static Gson gson = new Gson();
	private static Gson exclusedGson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	private static Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

	public static String getJson(Object obj) {
		return gson.toJson(obj);
	}

	public static String getOnlyExposedJson(Object obj) {
		return exclusedGson.toJson(obj);
	}

	public static String getPrettyJson(Object obj) {
		return prettyGson.toJson(obj);
	}

	public static <T> T getObject(String json, Class<T> clazz) {
		return gson.fromJson(json, clazz);
	}

	public static Map<String, Long> offsetMap(String json) {
		if (json == null || json.isEmpty()) {
			return new HashMap<>();
		}
		Type type = new TypeToken<Map<String, Long>>() {
		}.getType();
		return gson.fromJson(json, type);
	}
}