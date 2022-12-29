package com.sample.app.tags;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.hubspot.jinjava.Jinjava;
import com.sample.app.util.FileUtil;

public class ForOverMap {
	public static void main(String[] args) throws IOException {
		final Jinjava jinjava = new Jinjava();
		
		final Map<String, String> coutryCapitals = new HashMap<>();
		coutryCapitals.put("India", "New Delhi");
		coutryCapitals.put("Russia", "Moscow");
		coutryCapitals.put("Japan", "Tokyo");
		
		final Map<String, Object> data = new HashMap<>();
		data.put("coutryCapitals", coutryCapitals);

		final String template = FileUtil.resourceAsString("tags/forOverMap.jinja");

		final String finalDocument = jinjava.render(template, data);
		System.out.println(finalDocument);
	}
}
