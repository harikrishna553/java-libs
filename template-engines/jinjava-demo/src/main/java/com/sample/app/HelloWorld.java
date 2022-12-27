package com.sample.app;

import java.util.Map;

import com.google.common.collect.Maps;
import com.hubspot.jinjava.Jinjava;

public class HelloWorld {

	public static void main(final String[] args) {

		final Jinjava jinjava = new Jinjava();

		final Map<String, Object> data = Maps.newHashMap();
		data.put("name", "Krishna");
		data.put("age", 34);

		final String template = "Hello, My name is {{name}}, and I am {{age}} years old!";

		final String finalDocument = jinjava.render(template, data);
		System.out.println(finalDocument);
	}
}