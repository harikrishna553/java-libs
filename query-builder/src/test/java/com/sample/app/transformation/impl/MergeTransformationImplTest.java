package com.sample.app.transformation.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sample.app.exception.TransformationPayloadException;

public class MergeTransformationImplTest {
	
	@Test
	public void test1() throws TransformationPayloadException {
		String json = "   {\n"
				+ "\n"
				+ "      \"sourceFields\": [\n"
				+ "\n"
				+ "        \"week_start_date\",\n"
				+ "\n"
				+ "        \"review_date\"\n"
				+ "\n"
				+ "      ],\n"
				+ "\n"
				+ "      \"delimter\": \",\",\n"
				+ "\n"
				+ "      \"newFieldName\": \"sr_date\"\n"
				+ "\n"
				+ "    }";
		
		
		MergeTransformationImpl mergeTransformation = new MergeTransformationImpl();
		JsonElement jsonElement = JsonParser.parseString(json);
		mergeTransformation.setJsonObject((JsonObject) jsonElement);
		
		String queryString = mergeTransformation.getQueryString();
		String expectedResult = "CONCAT(week_start_date,',',review_date) AS sr_date";
		assertEquals(expectedResult, queryString);
		
	}

}
