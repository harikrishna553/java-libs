package com.sample.app;

import java.util.Map;

import com.google.common.collect.Maps;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.interpret.DeferredValue;

public class DeferNestedExpressions {
	public static void main(String[] args) {
		final Jinjava jinjava = new Jinjava();

		final Map<String, Object> data = Maps.newHashMap();
		data.put("emp", DeferredValue.instance());

		final String template = "Hello, My name is {{emp.name}}, and I am {{emp.age}} years old!";

		final String finalDocument = jinjava.render(template, data);
		System.out.println(finalDocument);
	}
}
