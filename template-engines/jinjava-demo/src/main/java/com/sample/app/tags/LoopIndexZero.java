package com.sample.app.tags;

import java.io.IOException;
import java.util.Collections;

import com.hubspot.jinjava.Jinjava;
import com.sample.app.util.FileUtil;

public class LoopIndexZero {
	public static void main(String[] args) throws IOException {
		final Jinjava jinjava = new Jinjava();

		final String template = FileUtil.resourceAsString("tags/loopIndexZero.jinja");

		final String finalDocument = jinjava.render(template, Collections.emptyMap());
		System.out.println(finalDocument);
	}
}
