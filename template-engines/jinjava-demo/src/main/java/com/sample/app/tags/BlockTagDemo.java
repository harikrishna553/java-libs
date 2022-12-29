package com.sample.app.tags;

import java.io.IOException;
import java.util.Collections;

import com.hubspot.jinjava.Jinjava;
import com.sample.app.util.FileUtil;

public class BlockTagDemo {
	public static void main(String[] args) throws IOException {
		final Jinjava jinjava = new Jinjava();

		final String parentTemplate = FileUtil.resourceAsString("tags/parentTemplate.jinja");
		final String parentDocument = jinjava.render(parentTemplate, Collections.emptyMap());
		System.out.println(parentDocument);

		System.out.println("\n***********************************\n");
		final String childTemplate = FileUtil.resourceAsString("tags/childTemplate.jinja");
		final String childDocument = jinjava.render(childTemplate, Collections.emptyMap());
		System.out.println(childDocument);
	}
}
