package com.sample.app.filters;

import java.io.IOException;
import java.util.Collections;

import com.hubspot.jinjava.Jinjava;
import com.sample.app.util.FileUtil;

public class ListFilterDemo {
	public static void main(String[] args) throws IOException {
		final Jinjava jinjava = new Jinjava();

		final String template = FileUtil.resourceAsString("filters/listFilter.jinja");

		final String finalDocument = jinjava.render(template, Collections.emptyMap()).trim();
		System.out.println(finalDocument);
	}
}
