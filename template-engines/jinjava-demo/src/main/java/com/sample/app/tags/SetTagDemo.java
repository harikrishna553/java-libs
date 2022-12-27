package com.sample.app.tags;

import java.io.IOException;
import java.util.Collections;

import com.hubspot.jinjava.Jinjava;
import com.sample.app.util.FileUtil;

public class SetTagDemo {

	public static void main(String[] args) throws IOException {
		final Jinjava jinjava = new Jinjava();

		final String template1 = FileUtil.resourceAsString("tags/setTag1.jinja");

		final String template2 = FileUtil.resourceAsString("tags/setTag2.jinja");

		final String finalDocument1 = jinjava.render(template1, Collections.EMPTY_MAP).trim();
		final String finalDocument2 = jinjava.render(template2, Collections.EMPTY_MAP).trim();

		System.out.println(finalDocument1);
		System.out.println(finalDocument2);
	}

}
