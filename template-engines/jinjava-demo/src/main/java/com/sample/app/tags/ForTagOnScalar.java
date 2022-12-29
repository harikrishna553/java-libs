package com.sample.app.tags;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.hubspot.jinjava.Jinjava;
import com.sample.app.util.FileUtil;

public class ForTagOnScalar {
	public static void main(String[] args) throws IOException {
		final Jinjava jinjava = new Jinjava();

		final String template = FileUtil.resourceAsString("tags/forLoopOnScalar.jinja");
		
		final Map<String, Object> data = new HashMap<> ();
		data.put("primes", 2);

		final String finalDocument = jinjava.render(template, data).trim();
		System.out.println(finalDocument);
	}

}
