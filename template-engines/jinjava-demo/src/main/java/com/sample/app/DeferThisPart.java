package com.sample.app;

import java.util.Map;

import com.google.common.collect.Maps;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.interpret.DeferredValue;

public class DeferThisPart {
	public static void main(final String[] args) {

		final Jinjava jinjava = new Jinjava();

		final DeferredValue deferThisPart = DeferredValue.instance();

		final Map<String, Object> data = Maps.newHashMap();
		data.put("placeholder1", deferThisPart);
		data.put("placeholder2", "jinjava tutorial");

		final String template = "'placeholder1' is not processed {{placeholder1}} \n'placeholder2' is processed '{{placeholder2}}'";

		final String finalDocument = jinjava.render(template, data);
		System.out.println(finalDocument);
	}
}
