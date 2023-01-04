package com.sample.app.filters;

import java.io.IOException;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.hubspot.jinjava.Jinjava;

public class FromYamlFilterDemo {
	
	public static void main(String[] args) throws IOException {
		final Jinjava jinjava = new Jinjava();
		String yamlStr = "a : 1\n" + "b : 2\n" + "c : 3";

	    Map<String, Object> vars = ImmutableMap.of("test", yamlStr);
	    String template = "{% set obj = test | fromyaml %} a : {{ obj.a }}, b : {{ obj.b }}, c : {{ obj.c }}";
	    String renderedJinjava = jinjava.render(template, vars);
	    
	    System.out.println(renderedJinjava);

	}
}
