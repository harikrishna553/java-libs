package com.sample.app;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.interpret.Context;
import com.hubspot.jinjava.interpret.RenderResult;
import com.hubspot.jinjava.interpret.TemplateError;

public class RenderResultDemo {

	private static void printRenderResult(RenderResult renderResult) {
		System.out.println("---------------------------------------------");
		String output = renderResult.getOutput();
		Context dataUsedWhileRendering = renderResult.getContext();
		List<TemplateError> templateErrros = renderResult.getErrors();

		System.out.println("output : " + output);
		System.out.println("dataUsedWhileRendering : ");
		dataUsedWhileRendering.keySet().forEach(key ->{
			System.out.println(key + " -> " + dataUsedWhileRendering.get(key));
		});
		for (TemplateError templateError : templateErrros) {
			System.out.println(templateError.getFieldName() + " -> " + templateError.getMessage());
		}
		System.out.println("---------------------------------------------\n");
	}

	public static void main(String[] args) {
		final Jinjava jinjava = new Jinjava();

		final Map<String, Object> data = Maps.newHashMap();
		data.put("a", 1);
		data.put("b", 2);
		data.put("d", 4);

		final String tempate1 = "a = {{a}}, b = {{b}}";
		final String tempate2 = "a = {{a}}, b = {{b}}, c ={{c}}";

		final RenderResult renderResult1 = jinjava.renderForResult(tempate1, data);
		final RenderResult renderResult2 = jinjava.renderForResult(tempate2, data);

		printRenderResult(renderResult1);
		printRenderResult(renderResult2);

	}

}
