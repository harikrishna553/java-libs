package com.sample.app.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

}