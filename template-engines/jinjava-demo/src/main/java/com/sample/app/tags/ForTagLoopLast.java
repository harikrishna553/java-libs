package com.sample.app.tags;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.hubspot.jinjava.Jinjava;
import com.sample.app.util.FileUtil;

public class ForTagLoopLast {

	public static void main(String[] args) throws IOException {
		final Jinjava jinjava = new Jinjava();

		final String template = FileUtil.resourceAsString("tags/forLoopLast.jinja");

		final Map<String, Object> data = new HashMap<>();
		data.put("primes", Arrays.asList(2, 3, 5, 7));

		final String finalDocument = jinjava.render(template, data);
		System.out.println(finalDocument);
	}

}
