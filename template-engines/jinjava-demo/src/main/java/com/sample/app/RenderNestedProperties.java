package com.sample.app;

import java.util.Map;

import com.google.common.collect.Maps;
import com.hubspot.jinjava.Jinjava;
import com.sample.app.model.Employee;

public class RenderNestedProperties {

	public static void main(String[] args) {
		final Employee employee = new Employee(1, "Krishna", 35);

		final Jinjava jinjava = new Jinjava();

		final Map<String, Object> data = Maps.newHashMap();
		data.put("emp", employee);

		final String template = "Hello, My name is {{emp.name}}, and I am {{emp.age}} years old!";

		final String finalDocument = jinjava.render(template, data);
		System.out.println(finalDocument);
	}

}
