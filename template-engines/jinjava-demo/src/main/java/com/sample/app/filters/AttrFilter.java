package com.sample.app.filters;

import java.io.IOException;
import java.util.Map;

import com.google.common.collect.Maps;
import com.hubspot.jinjava.Jinjava;
import com.sample.app.model.Employee;
import com.sample.app.util.FileUtil;

public class AttrFilter {
	public static void main(String[] args) throws IOException {
		final Jinjava jinjava = new Jinjava();

		final String template = FileUtil.resourceAsString("filters/attrFilter.jinja");

		final Map<String, Object> data = Maps.newHashMap();
		final Employee employee = new Employee(1, "Krishna", 35);
		data.put("emp", employee);

		final String finalDocument = jinjava.render(template, data);
		System.out.println(finalDocument);
	}
}
